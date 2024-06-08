package monitoring_ozone.MVC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static monitoring_ozone.constants.Route.LOGIN;

@Controller
@RequestMapping(LOGIN)
public class LoginController {

    @GetMapping("")
    public String login() {
        return "login";
    }
}
