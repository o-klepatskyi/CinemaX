package ua.edu.ukma.cinemax.media

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/actuator/health/test")
class CustomHealthController {
    @GetMapping
    fun health(): String {
        return "OK"
    }
}