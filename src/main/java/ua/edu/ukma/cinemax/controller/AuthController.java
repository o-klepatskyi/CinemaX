package ua.edu.ukma.cinemax.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.edu.ukma.cinemax.dto.UserDto;
import ua.edu.ukma.cinemax.security.model.Roles;
import ua.edu.ukma.cinemax.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final UserService userService;

    @GetMapping("/about")
    public String aboutWindow() {
        return "/about";
    }

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

    @GetMapping("/404")
    public String error404() {
        return "/error/404";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "register";
    }

    @GetMapping("/register/admin")
    public String showAdminRegistrationForm(Model model){
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "admin-register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        user.setRoles(List.of(Roles.USER.name()));
        try {
            userService.add(user);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            return "redirect:/register?error";
        }
        return "redirect:/register?success";
    }

    @PostMapping("/register/admin/save")
    public String adminRegistration(@Valid @ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "admin-register";
        }
        user.setRoles(List.of(Roles.ADMIN.name(), Roles.USER.name()));
        try {
            userService.add(user);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            return "redirect:/register?error";
        }
        return "redirect:/register/admin?success";
    }

    @GetMapping("/users")
    public ModelAndView users(Model model){
        List<UserDto> users = userService.getAll();
        model.addAttribute("users", users);
        return new ModelAndView("users");
    }

}
