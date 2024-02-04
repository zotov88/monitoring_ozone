package monitoring_ozone.service;

import lombok.RequiredArgsConstructor;
import monitoring_ozone.model.Product;
import monitoring_ozone.model.Story;
import monitoring_ozone.repository.StoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StoryService {

    private final StoryRepository repository;

    public void create(final Product product, final int price) {
        repository.save(Story.builder()
                .product(product)
                .price(price)
                .date(LocalDate.now())
                .build());
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

    public Map<LocalDate, Integer> getLast20StoryRecordsMap(Map<LocalDate, Integer> storyRecordsMap, int size) {
        Map<LocalDate, Integer> lastNStoriesRecordsMap = new TreeMap<>();
        Map<LocalDate, Integer> reverseMap = new TreeMap<>(Collections.reverseOrder());
        reverseMap.putAll(storyRecordsMap);
        for (LocalDate localDate : reverseMap.keySet()) {
            lastNStoriesRecordsMap.put(localDate, reverseMap.get(localDate));
            if (--size == 0) {
                break;
            }
        }
        return lastNStoriesRecordsMap;
    }
}
