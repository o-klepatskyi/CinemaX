package ua.edu.ukma.cinemax.controller;

import ua.edu.ukma.cinemax.repository.impl.UserCredentials;
import ua.edu.ukma.cinemax.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
public class OAuthController {

    private final UserService userService;

    // constructor inject
    @Autowired
    public OAuthController(UserService userService) {
        this.userService = userService;
    }

    // methods login

    public void checkCredentials() {
        UserCredentials myUser = userService.getCredentials("myLogin");
        // do some checks...
    }
}
