package monitoring_ozone.service;

import monitoring_ozone.model.Product;
import monitoring_ozone.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public void create(Product product) {
        repository.save(product);
    }

    public List<Product> getAllByUserId(Long id) {
        return repository.findAllByUserIdN(id);
    }

    public void update(Product product) {
        repository.save(product);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Product getOne(Long id) {
        return repository.getReferenceById(id);
    }
}
