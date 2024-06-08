package monitoring_ozone.MVC.controller;

import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import monitoring_ozone.model.User;
import monitoring_ozone.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.naming.AuthenticationException;

import java.util.Objects;

import static monitoring_ozone.constants.RolesConstants.ADMIN;
import static monitoring_ozone.constants.Route.REGISTRATION;

@Controller
@RequestMapping(REGISTRATION)
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @GetMapping("")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("")
    public String registration(@ModelAttribute(name = "userForm") User user,
                               BindingResult bindingResult) throws AuthenticationException {
        String login = user.getLogin().toLowerCase();
        String email = user.getEmail().toLowerCase();
        if (StringUtil.isNullOrEmpty(login)) {
            throw new AuthenticationException("Not allowed to register user: " + login);
        }
        if (StringUtil.isNullOrEmpty(email)) {
            throw new AuthenticationException("Not correct email: " + email);
        }
        user.setLogin(login);
        user.setEmail(email);
        if (login.equalsIgnoreCase(ADMIN) || Objects.nonNull(userService.getByLogin(login))) {
            bindingResult.rejectValue("login", "login.error", "Этот логин уже существует");
            return "registration";
        }
        if (Objects.nonNull(userService.getByEmail(email))) {
            bindingResult.rejectValue("email", "email.error", "Этот email уже существует");
            return "registration";
        }
        userService.create(user);
        return "redirect:/login";
    }


}
