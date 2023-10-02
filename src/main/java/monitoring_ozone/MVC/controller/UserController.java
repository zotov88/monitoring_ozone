package monitoring_ozone.MVC.controller;

import monitoring_ozone.model.User;
import monitoring_ozone.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile/{id}")
    public String profile(@PathVariable Long id,
                          Model model) {
        model.addAttribute("userForm", userService.getById(id));
        return "user/profile";
    }

    @GetMapping("/profile/update/{id}")
    public String update(@PathVariable Long id,
                         Model model) {
        model.addAttribute("userForm", userService.getById(id));
        return "user/updateUser";
    }

    @PostMapping("/profile/update")
    public String update(@ModelAttribute("userForm") User userUpdated) {
        User foundUser = userService.getById(userUpdated.getId());
        foundUser.setName(userUpdated.getName());
        foundUser.setTgBotToken(userUpdated.getTgBotToken());
        foundUser.setTgChatId(foundUser.getTgChatId());
        userService.update(foundUser);
        return "redirect:/products/all/" +
                userService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
    }
}
