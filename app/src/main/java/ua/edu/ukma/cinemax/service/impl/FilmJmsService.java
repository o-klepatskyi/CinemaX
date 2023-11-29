package ua.edu.ukma.cinemax.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import ua.edu.ukma.cinemax.dto.FilmDto;
import ua.edu.ukma.cinemax.dto.JmsFilmMessage;
import ua.edu.ukma.cinemax.dto.JmsFilmResponse;
import ua.edu.ukma.cinemax.service.FilmService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmJmsService implements FilmService {
    private static final String MAIN_RESPONSE_QUEUE = "FilmServiceResponseQueue";
    private static final String FILM_DESTINATION_QUEUE = "FilmServiceRequestQueue";
    private final JmsTemplate jmsTemplate;

    @Override
    public void add(FilmDto film) {
        createAndSendMessage("createFilm", film);

        String jsonResponse = (String) jmsTemplate.receiveAndConvert(MAIN_RESPONSE_QUEUE);

        JmsFilmResponse response = convertResponse(jsonResponse);
        assertResponseIsSuccess(response);
    }

    @Override
    public FilmDto get(Long id) {
        createAndSendMessage("getFilmById", id);

        String jsonResponse = (String) jmsTemplate.receiveAndConvert(MAIN_RESPONSE_QUEUE);

        JmsFilmResponse response = convertResponse(jsonResponse);
        assertResponseIsSuccess(response);
        return response.getFilmDto();
    }

    @Override
    public List<FilmDto> getAll() {
        createAndSendMessage("getAllFilms");

        String jsonResponse = (String) jmsTemplate.receiveAndConvert(MAIN_RESPONSE_QUEUE);

        JmsFilmResponse response = convertResponse(jsonResponse);
        assertResponseIsSuccess(response);
        return response.getFilmDtoList();
    }


    @Override
    public void update(FilmDto film) {
        createAndSendMessage("updateFilm", film);

        String jsonResponse = (String) jmsTemplate.receiveAndConvert(MAIN_RESPONSE_QUEUE);

        JmsFilmResponse response = convertResponse(jsonResponse);
        assertResponseIsSuccess(response);
    }

    @Override
    public void delete(Long id) {
        createAndSendMessage("deleteFilm", id);

        String jsonResponse = (String) jmsTemplate.receiveAndConvert(MAIN_RESPONSE_QUEUE);

        JmsFilmResponse response = convertResponse(jsonResponse);
        assertResponseIsSuccess(response);
    }

    private static void assertResponseIsSuccess(JmsFilmResponse response) {
        if (response == null || !response.isSuccess()) {
            throw new RuntimeException();
        }
    }

    private void createAndSendMessage(String method) {
        JmsFilmMessage message = JmsFilmMessage.builder()
                .method(method)
                .build();
        sendFilmMessage(message);
    }


    private void createAndSendMessage(String method, Long id) {
        JmsFilmMessage message = JmsFilmMessage.builder()
                .method(method)
                .id(id)
                .build();
        sendFilmMessage(message);
    }

    private void createAndSendMessage(String method, FilmDto film) {
        JmsFilmMessage message = JmsFilmMessage.builder()
                .method(method)
                .filmDto(film)
                .build();
        sendFilmMessage(message);
    }

    private JmsFilmResponse convertResponse(String jsonResponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonResponse, JmsFilmResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendFilmMessage(JmsFilmMessage message) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMessage;
        try {
            jsonMessage = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        jmsTemplate.convertAndSend(FILM_DESTINATION_QUEUE, jsonMessage);
    }
}
