package ua.edu.ukma.cinemax.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.edu.ukma.cinemax.dto.SessionDto;
import ua.edu.ukma.cinemax.persistance.entity.Session;
import ua.edu.ukma.cinemax.service.CinemaHallService;
import ua.edu.ukma.cinemax.service.FilmService;
import ua.edu.ukma.cinemax.service.SessionService;

@Controller
@RequiredArgsConstructor
public class SessionController {
    Logger logger = LoggerFactory.getLogger(SessionController.class);
    private final SessionService sessionService;
    private final FilmService filmService;
    private final CinemaHallService cinemaHallService;

    @GetMapping("/session/add")
    public String add(Model model) {
        SessionDto sessionDto = new SessionDto();
        model.addAttribute("session", sessionDto)
                .addAttribute("films", filmService.getAll())
                .addAttribute("cinemaHalls", cinemaHallService.getAll());
        return "session/add";
    }

    @PostMapping("/session/add")
    public String submitNewSession(@Valid @ModelAttribute("session") SessionDto sessionDto,
                                BindingResult result, Model model) {
        logger.info(sessionDto.toString());
        if (result.hasErrors()) {
            return "session/add";
        }
        try {
            sessionService.add(sessionDto);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            model.addAttribute("errorValue", "Cinema hall is occupied on that time.\n" +
                            "(Note: session duration is 2 hours)")
                    .addAttribute("session", sessionDto)
                    .addAttribute("films", filmService.getAll())
                    .addAttribute("cinemaHalls", cinemaHallService.getAll())
                    .addAttribute("dateValue", LocalDate.now())
                    .addAttribute("timeValue", LocalTime.now());
            return "/session/add";
        }
        return "redirect:/session/add?success";
    }

    @GetMapping("/session/all")
    public String selectAll(Model model) {
        List<SessionDto> sessions = sessionService.get();
        model.addAttribute("sessions", sessions);
        return "session/all";
    }

    @GetMapping("/session/all/{id}/{date}")
    public String selectAllAvailable(@PathVariable Long id,
                                               @PathVariable LocalDate date) {
        ModelAndView mav = new ModelAndView("list-sessions");
        List<SessionDto> sessions = sessionService.getAvailableSessions(id, date);
        mav.addObject("sessions", sessions);
        return "session/all";
    }

    @GetMapping(path = "/session/edit/{id}")
    public String getEditPage(@PathVariable Long id, Model model) {
        Session session = sessionService.get(id);
        model.addAttribute("session", session)
                .addAttribute("films", filmService.getAll())
                .addAttribute("cinemaHalls", cinemaHallService.getAll());;
        return "session/edit";
    }

    @PostMapping(path = "/session/edit/{id}")
    public String edit(@Valid @ModelAttribute("session") SessionDto session,
                       BindingResult result) {
        if (result.hasErrors()) {
            return "session/edit";
        }
        try {
            sessionService.update(session);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            return String.format("redirect:/session/edit/%s?error", session.getId());
        }
        return String.format("redirect:/session/edit/%s?success", session.getId());
    }

    @DeleteMapping(path = "/session/delete/{id}")
    public String delete(@PathVariable Long id) {
        sessionService.delete(id);
        return "session/all";
    }
}
