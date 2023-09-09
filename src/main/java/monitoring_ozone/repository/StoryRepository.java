package monitoring_ozone.repository;

import monitoring_ozone.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Long> {

    @Query(nativeQuery = true,
            value = """
                    select *
                    from ozone.stories
                    where product_id = :id
                    """)
    List<Story> findAllById(Long id);
}
