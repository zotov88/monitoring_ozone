package monitoring_ozone.MVC.controller;

import monitoring_ozone.service.StoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/story")
public class StoryController {

    private final StoryService storyService;

    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    @GetMapping("/{id}")
    public String productStory(@PathVariable Long id,
                               Model model) {
        model.addAttribute("productList", storyService.getStoryList(id));
        return "storyList";
    }

}
