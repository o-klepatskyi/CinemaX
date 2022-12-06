package ua.edu.ukma.cinemax.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class HomeController {

    @GetMapping("/")
    public String hello() {
        return "Hello, World";
    }
}
