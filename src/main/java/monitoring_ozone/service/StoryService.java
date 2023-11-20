package monitoring_ozone.service;

import monitoring_ozone.model.Product;
import monitoring_ozone.model.Story;
import monitoring_ozone.repository.StoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class StoryService {

    private final StoryRepository repository;

    public StoryService(StoryRepository repository) {
        this.repository = repository;
    }

    public void create(Product product, final int price) {
        Story story = new Story();
        story.setProduct(product);
        story.setPrice(price);
        story.setDate(LocalDate.now());
        repository.save(story);
    }

    public List<Story> getStoryList(Long id) {
        return repository.findAllById(id);
    }

    public LocalDate getDateFromMap(Map<LocalDate, Integer> map, Integer price) {
        LocalDate localDate = null;
        for (LocalDate date : map.keySet()) {
            if (Objects.equals(map.get(date), price)) {
                localDate = date;
            }
        }
        return localDate;
    }

    public Integer getMinPriceByProductId(Long productId) {
        return repository.findMinPriceByProductId(productId);
    }

    public Map<LocalDate, Integer> getStoryRecordsMap(Long productId) {
        Map<LocalDate, Integer> storyRecordsMap = new TreeMap<>();
        for (Story story : getStoryList(productId)) {
            if (!storyRecordsMap.containsKey(story.getDate())) {
                storyRecordsMap.put(story.getDate(), story.getPrice());
            } else if (story.getPrice() < storyRecordsMap.get(story.getDate())) {
                storyRecordsMap.put(story.getDate(), story.getPrice());
            }
        }
        return storyRecordsMap;
    }

    public Map<LocalDate, Integer> getLast20StoryRecordsMap(Map<LocalDate, Integer> storyRecordsMap) {
        Map<LocalDate, Integer> last20StoryRecordsMap = new TreeMap<>();
        Map<LocalDate, Integer> reverseMap = new TreeMap<>(Collections.reverseOrder());
        reverseMap.putAll(storyRecordsMap);
        int count = 20;
        for (LocalDate localDate : reverseMap.keySet()) {
            last20StoryRecordsMap.put(localDate, reverseMap.get(localDate));
            if (--count == 0) {
                break;
            }
        }
        return last20StoryRecordsMap;
    }
}
