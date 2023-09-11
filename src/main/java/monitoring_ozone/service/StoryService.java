package monitoring_ozone.service;

import monitoring_ozone.model.Product;
import monitoring_ozone.model.Story;
import monitoring_ozone.repository.StoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
}
