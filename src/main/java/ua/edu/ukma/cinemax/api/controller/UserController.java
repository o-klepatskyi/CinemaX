package ua.edu.ukma.cinemax.api.controller;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.ukma.cinemax.api.model.ApiUser;
import ua.edu.ukma.cinemax.exception.InvalidUserDataException;
import ua.edu.ukma.cinemax.model.User;
import ua.edu.ukma.cinemax.service.UserService;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private static int requestId = 0;
    final static Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @PostMapping("/add")
    public void add(@Valid @RequestBody ApiUser user) {
        MDC.put("request_id", "user/add/:request_id: " + requestId++);
        userService.add(user.toModel());
        MDC.clear();
    }

    @GetMapping(
            path = "/all",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<ApiUser> selectAll() {
        List<User> users = userService.getAll();
        List<ApiUser> apiUsers = new ArrayList<>(users.size());
        users.forEach(u -> apiUsers.add(new ApiUser(u)));
        return apiUsers;
    }

    @PutMapping(path = "edit/{id}")
    public void edit(@PathVariable Long id,
                     @RequestBody ApiUser user) {
        user.setId(id);
        userService.update(user.toModel());
    }

    @DeleteMapping(path = "delete/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
