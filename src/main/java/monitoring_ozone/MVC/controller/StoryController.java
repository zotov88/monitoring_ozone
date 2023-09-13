package monitoring_ozone.MVC.controller;

import monitoring_ozone.model.Story;
import monitoring_ozone.service.ProductService;
import monitoring_ozone.service.StoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/story")
public class StoryController {

    private final StoryService storyService;
    private final ProductService productService;

    public StoryController(StoryService storyService,
                           ProductService productService) {
        this.storyService = storyService;
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public String productStory(@PathVariable Long id,
                               Model model) {
        Map<LocalDate, Integer> map = new LinkedHashMap<>();
        for (Story story : storyService.getStoryList(id)) {
            map.put(story.getDate(), story.getPrice());
        }
        Integer maxVal = Collections.max(map.values());
        Integer minVal = Collections.min(map.values());
        model.addAttribute("keySet", map.keySet());
        model.addAttribute("values", map.values());
        model.addAttribute("graphHeight", maxVal * 1.1);
        model.addAttribute("max", maxVal);
        model.addAttribute("min", minVal);
        model.addAttribute("dateMaxVal", getDateFromMap(map, maxVal));
        model.addAttribute("dateMinVal", getDateFromMap(map, minVal));
        model.addAttribute("productName", productService.getOne(id).getName());
        return "history";
    }

    private LocalDate getDateFromMap(Map<LocalDate, Integer> map, Integer max) {
        LocalDate localDate = null;
        for (LocalDate date : map.keySet()) {
            if (Objects.equals(map.get(date), max)) {
                localDate = date;
            }
        }
        return localDate;
    }

}