package monitoring_ozone.service;

import monitoring_ozone.model.Story;
import monitoring_ozone.repository.StoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoryService {

    private final StoryRepository repository;

    public StoryService(StoryRepository repository) {
        this.repository = repository;
    }

    public void add(Story story) {
        repository.save(story);
    }

    public List<Story> getStoryList(Long id) {
        return repository.findAllById(id);
    }
}
