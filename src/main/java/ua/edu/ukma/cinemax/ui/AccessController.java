package ua.edu.ukma.cinemax.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccessController {

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @PostMapping("/login")
    public String doLogin() {
        return "redirect:/film/all";
    }

    @GetMapping("/logout")
    public String logout() {
        return "/logout";
    }

    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }
}
