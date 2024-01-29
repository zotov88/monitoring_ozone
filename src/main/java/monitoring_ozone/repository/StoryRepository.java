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
                        and price <> 0
                    """)
    List<Story> findAllById(Long id);

    @Query(nativeQuery = true,
            value = """
                    select price
                    from ozone.stories
                    where product_id = :productId
                        and price <> 0
                    order by price
                    limit 1
                    """)
    Integer findMinPriceByProductId(Long productId);
}
