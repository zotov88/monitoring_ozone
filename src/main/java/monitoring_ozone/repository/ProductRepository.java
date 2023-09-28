package monitoring_ozone.repository;

import monitoring_ozone.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(nativeQuery = true,
    value = """
            select *
            from ozone.products
            where user_id = :id
            """)
    List<Product> findAllByUserIdN(Long id);
}
