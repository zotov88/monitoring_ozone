package monitoring_ozone.MVC.controller;

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
    public String productStory(@PathVariable Long productId, Model model) {
        Map<LocalDate, Integer> storyRecordsMap = storyService.getStoryRecordsMap(productId);
        Map<LocalDate, Integer> last20StoryRecordsMap = storyService.getLast20StoryRecordsMap(storyRecordsMap);

        int maxVal = Collections.max(storyRecordsMap.values());
        int minVal = Collections.min(storyRecordsMap.values());

        model.addAttribute("keySet", last20StoryRecordsMap.keySet());
        model.addAttribute("values", last20StoryRecordsMap.values());
        model.addAttribute("graphHeight", maxVal * 1.1);
        model.addAttribute("max", maxVal);
        model.addAttribute("min", Collections.min(storyRecordsMap.values()));
        model.addAttribute("dateMaxVal", storyService.getDateFromMap(storyRecordsMap, maxVal));
        model.addAttribute("dateMinVal", storyService.getDateFromMap(storyRecordsMap, minVal));
        model.addAttribute("productName", productService.getOne(productId).getName());
        return "history/history";
    }

}
