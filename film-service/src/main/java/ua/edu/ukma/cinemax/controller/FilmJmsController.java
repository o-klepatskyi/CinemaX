package ua.edu.ukma.cinemax.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import ua.edu.ukma.cinemax.dto.FilmDto;
import ua.edu.ukma.cinemax.dto.JmsFilmMessage;
import ua.edu.ukma.cinemax.dto.JmsFilmResponse;
import ua.edu.ukma.cinemax.service.FilmService;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Component
public class FilmJmsController {
    private static final String MAIN_RESPONSE_QUEUE = "FilmServiceResponseQueue";
    private static final String FILM_REQUEST_QUEUE = "FilmServiceRequestQueue";
    private final JmsTemplate jmsTemplate;
    private final FilmService filmService;

    @JmsListener(destination = FILM_REQUEST_QUEUE)
    public void processJmsMessage(String jsonMessage) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JmsFilmMessage message = objectMapper.readValue(jsonMessage, JmsFilmMessage.class);

        try {
            switch (message.getMethod()) {
                case "getFilmById" -> processGetFilmById(message);
                case "getAllFilms" -> processGetAllFilms();
                case "createFilm" -> processCreateFilm(message);
                case "updateFilm" -> processUpdateFilm(message);
                case "deleteFilm" -> processDeleteFilm(message);
                default -> throw new IllegalArgumentException("Invalid method name");
            }
        } catch (Exception e) {
            handleErrorResponse(e);
        }
    }

    private void processDeleteFilm(JmsFilmMessage message) throws JsonProcessingException {
        Long id = message.getId();

        filmService.delete(id);
        JmsFilmResponse deleteResponse = JmsFilmResponse.builder()
                .success(true)
                .build();
        sendResponse(deleteResponse);
    }

    private void processUpdateFilm(JmsFilmMessage message) throws JsonProcessingException {
        FilmDto updatedFilm = message.getFilmDto();
        FilmDto updatedResult = filmService.update(updatedFilm);
        JmsFilmResponse updateResponse = JmsFilmResponse.builder()
                .success(true)
                .filmDto(updatedResult)
                .build();
        sendResponse(updateResponse);
    }

    private void processCreateFilm(JmsFilmMessage message) throws JsonProcessingException {
        FilmDto filmDto = message.getFilmDto();
        FilmDto createdFilm = filmService.add(filmDto);
        JmsFilmResponse createResponse = JmsFilmResponse.builder()
                .success(true)
                .filmDto(createdFilm)
                .build();
        sendResponse(createResponse);
    }

    private void processGetAllFilms() throws JsonProcessingException {
        List<FilmDto> films = filmService.getAll();
        JmsFilmResponse.JmsFilmResponseBuilder responseBuilder = JmsFilmResponse.builder()
                .success(true)
                .filmDtoList(films);
        sendResponse(responseBuilder.build());
    }

    private void processGetFilmById(JmsFilmMessage message) throws JsonProcessingException {
        Optional<FilmDto> optionalFilmDto = filmService.get(message.getId());
        JmsFilmResponse.JmsFilmResponseBuilder responseBuilder = JmsFilmResponse.builder();

        if (optionalFilmDto.isPresent()) {
            responseBuilder.success(true)
                    .filmDto(optionalFilmDto.get());
        } else {
            responseBuilder.success(false);
        }

        sendResponse(responseBuilder.build());
    }

    private void sendResponse(JmsFilmResponse response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(response);
        jmsTemplate.convertAndSend(MAIN_RESPONSE_QUEUE, jsonResponse);
    }

    private void handleErrorResponse(Exception e) throws JsonProcessingException {
        JmsFilmResponse errorResponse = JmsFilmResponse.builder()
                .success(false)
                .errorMessage(e.getMessage())
                .build();
        sendResponse(errorResponse);
    }
}
