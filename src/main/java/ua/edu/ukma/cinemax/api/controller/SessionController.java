package ua.edu.ukma.cinemax.api.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.ukma.cinemax.api.model.ApiSession;
import ua.edu.ukma.cinemax.model.Session;
import ua.edu.ukma.cinemax.service.SessionService;

@RestController
@AllArgsConstructor
@RequestMapping("/session")
public class SessionController {
    private static int requestId = 0;
    final static Logger logger = LoggerFactory.getLogger(SessionController.class);

    private final SessionService sessionService;

    @PostMapping("/add")
    public void add(@RequestBody ApiSession session) {
        MDC.put("request_id", "session/add/:request_id: " + requestId++);
        sessionService.add(session.toModel());
        MDC.clear();
    }

    @GetMapping(
            path = "/available/{id}/{date}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<ApiSession> selectAllAvailable(@PathVariable Long id,
                                               @PathVariable LocalDate date) {
        List<Session> sessions = sessionService.getAvailableSessions(id, date);
        List<ApiSession> apiSessions = new ArrayList<>(sessions.size());
        sessions.forEach(s -> apiSessions.add(new ApiSession(s)));
        return apiSessions;
    }

    @PutMapping(path = "edit/{id}")
    public void edit(@PathVariable Long id,
                     @RequestBody ApiSession session) {
        session.setId(id);
        sessionService.update(session.toModel());
    }

    @DeleteMapping(path = "delete/{id}")
    public void delete(@PathVariable Long id) {
        sessionService.delete(id);
    }
}
