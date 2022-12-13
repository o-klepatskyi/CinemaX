package ua.edu.ukma.cinemax.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.edu.ukma.cinemax.dto.UserDto;
import ua.edu.ukma.cinemax.persistance.model.User;
import ua.edu.ukma.cinemax.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }

    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model){
        User existing = userService.get(user.getUsername());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.add(user);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public ModelAndView users(Model model){
        List<UserDto> users = userService.getAll();
        model.addAttribute("users", users);
        return new ModelAndView("users");
    }

}
