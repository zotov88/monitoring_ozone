package monitoring_ozone.MVC.controller;

import monitoring_ozone.service.ProductService;
import monitoring_ozone.service.StoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;

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

    @GetMapping("/{productId}")
    public String productStory(@PathVariable Long productId,
                               @RequestParam(value = "size", defaultValue = "${story.config.size-default}") int size,
                               Model model) {
        Map<LocalDate, Integer> storyRecordsMap = storyService.getStoryRecordsMap(productId);
        Map<LocalDate, Integer> lastXStoryRecordsMap = storyService.getLast20StoryRecordsMap(storyRecordsMap, size);
        int maxVal = Collections.max(storyRecordsMap.values());
        int minVal = Collections.min(storyRecordsMap.values());
        model.addAttribute("keySet", lastXStoryRecordsMap.keySet());
        model.addAttribute("values", lastXStoryRecordsMap.values());
        model.addAttribute("graphHeight", Collections.max(lastXStoryRecordsMap.values()) * 1.01);
        model.addAttribute("max", maxVal);
        model.addAttribute("min", minVal);
        model.addAttribute("maxX", Collections.max(lastXStoryRecordsMap.values()));
        model.addAttribute("minX", Collections.min(lastXStoryRecordsMap.values()));
        model.addAttribute("dateMaxVal", storyService.getDateFromMap(storyRecordsMap, maxVal));
        model.addAttribute("dateMinVal", storyService.getDateFromMap(storyRecordsMap, minVal));
        model.addAttribute("productName", productService.getOne(productId).getName());
        model.addAttribute("sizeStory", size);

        return "history/history";
    }
}
