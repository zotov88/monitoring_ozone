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
import java.util.Map;
import java.util.TreeMap;

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
    public String productStory(@PathVariable Long id, Model model) {
        Map<LocalDate, Integer> dateWithPricesMap = new TreeMap<>();
        for (Story story : storyService.getStoryList(id)) {
            if (!dateWithPricesMap.containsKey(story.getDate())) {
                dateWithPricesMap.put(story.getDate(), story.getPrice());
            } else if (story.getPrice() < dateWithPricesMap.get(story.getDate())) {
                dateWithPricesMap.put(story.getDate(), story.getPrice());
            }
        }

        Integer maxVal = Collections.max(dateWithPricesMap.values());
        Integer minVal = storyService.getMinPriceFromCollection(dateWithPricesMap.values());
        model.addAttribute("keySet", dateWithPricesMap.keySet());
        model.addAttribute("values", dateWithPricesMap.values());
        model.addAttribute("graphHeight", maxVal * 1.1);
        model.addAttribute("max", maxVal);
        model.addAttribute("min", Collections.min(dateWithPricesMap.values()));
        model.addAttribute("dateMaxVal", storyService.getDateFromMap(dateWithPricesMap, maxVal));
        model.addAttribute("dateMinVal", storyService.getDateFromMap(dateWithPricesMap, minVal));
        model.addAttribute("productName", productService.getOne(id).getName());
        return "history/history";
    }

}
