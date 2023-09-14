package monitoring_ozone.service;

import monitoring_ozone.model.Product;
import monitoring_ozone.model.Role;
import monitoring_ozone.model.User;
import monitoring_ozone.repository.ProductRepository;
import monitoring_ozone.repository.UserRepository;
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

    public List<Product> getAll() {
        return repository.findAll();
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
