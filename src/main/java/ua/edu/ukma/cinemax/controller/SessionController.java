package ua.edu.ukma.cinemax.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.edu.ukma.cinemax.api.model.ApiSession;
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
        ApiSession apiSession = new ApiSession();
        model.addAttribute("session", apiSession)
                .addAttribute("films", filmService.getAll())
                .addAttribute("cinemaHalls", cinemaHallService.getAll())
                .addAttribute("dateValue", LocalDate.now())
                .addAttribute("timeValue", LocalTime.now());
        return "session/add";
    }

    @PostMapping("/session/add")
    public String submitNewSession(@Valid @ModelAttribute("session") ApiSession session,
                                BindingResult result, Model model) {
        logger.info(session.toString());
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Invalid session data");
            return "session/add";
        }
        sessionService.add(session.toModel());
        return "redirect:/session/add?success";
    }

    @GetMapping("/session/all")
    public String selectAll(Model model) {
        List<Session> sessions = sessionService.get();
        List<ApiSession> apiSessions = new ArrayList<>(sessions.size());
        sessions.forEach((s) -> apiSessions.add(new ApiSession(s)));
        sessions.forEach((s) -> logger.info(s.toString()));
        apiSessions.forEach((s) -> logger.info(s.toString()));
        model.addAttribute("sessions", apiSessions);
        return "session/all";
    }

    @GetMapping("/session/all/{id}/{date}")
    public String selectAllAvailable(@PathVariable Long id,
                                               @PathVariable Date date) {
        ModelAndView mav = new ModelAndView("list-sessions");
        List<Session> sessions = sessionService.getAvailableSessions(id, date);
        List<ApiSession> apiSessions = new ArrayList<>(sessions.size());
        sessions.forEach((s) -> apiSessions.add(new ApiSession(s)));
        mav.addObject("sessions", apiSessions);
        return "session/all";
    }

    @GetMapping(path = "/session/edit/{id}")
    public String getEditPage(@PathVariable Long id, Model model) {
        Session session = sessionService.get(id);
        model.addAttribute("session", session);
        return "session/edit";
    }

    @PostMapping(path = "/session/edit/{id}")
    public String edit(@Valid @ModelAttribute("session") ApiSession session,
                       BindingResult result) {
        if (result.hasErrors()) {
            return "session/edit";
        }
        sessionService.update(session.toModel());
        return String.format("redirect:/session/edit/%s?success", session.getId());
    }

    @DeleteMapping(path = "/session/delete/{id}")
    public String delete(@PathVariable Long id) {
        sessionService.delete(id);
        return "session/all";
    }
}
