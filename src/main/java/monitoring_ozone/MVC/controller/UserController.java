package monitoring_ozone.MVC.controller;

import jakarta.websocket.server.PathParam;
import monitoring_ozone.model.User;
import monitoring_ozone.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

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
        foundUser.setTgChatId(userUpdated.getTgChatId());
        userService.update(foundUser);
        return "redirect:/products/all/" +
                userService.getByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
    }

    @GetMapping("/remember-password")
    public String rememberPassword() {
        return "user/rememberPassword";
    }

    @PostMapping("/remember-password")
    public String rememberPassword(@ModelAttribute("changePasswordForm") User user) {
        user = userService.getByEmail(user.getEmail());
        if (Objects.isNull(user)) {
            return "user/notFoundEmail";
        }
        userService.sendChangePasswordEmail(user);
        return "redirect:/login";
    }

    @GetMapping("/change-password")
    public String changePassword(@PathParam(value = "uuid") String uuid,
                                 Model model) {
        model.addAttribute("uuid", uuid);
        if (Objects.isNull(userService.getUserByChangePasswordToken(uuid))) {
            return "user/usedToken";
        }
        return "user/changePassword";
    }

    @PostMapping("/change-password")
    public String changePassword(@PathParam(value = "uuid") String uuid,
                                 @ModelAttribute("changePasswordForm") User user) {
        userService.changePassword(uuid, user.getPassword());
        return "redirect:/logout";
    }
}
