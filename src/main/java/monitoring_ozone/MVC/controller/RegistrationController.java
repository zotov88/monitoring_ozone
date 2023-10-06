package monitoring_ozone.MVC.controller;

import monitoring_ozone.model.User;
import monitoring_ozone.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static monitoring_ozone.constants.RolesConstants.ADMIN;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("")
    public String registration(@ModelAttribute(name = "userForm") User user,
                               BindingResult bindingResult) {
        String login = user.getLogin().toLowerCase();
        String email = user.getEmail().toLowerCase();
        user.setLogin(login);
        user.setEmail(email);
        if (login.equalsIgnoreCase(ADMIN) || (userService.getByLogin(login) != null)) {
            bindingResult.rejectValue("login", "login.error", "Этот логин уже существует");
            return "registration";
        }
        if (userService.getByEmail(email) != null) {
            bindingResult.rejectValue("email", "email.error", "Этот email уже существует");
            return "registration";
        }
        userService.create(user);
        return "redirect:/login";
    }


}
