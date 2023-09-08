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

    public void addProduct(Product product) {
        repository.save(product);
    }

    public List<Product> getAll() {
        return repository.findAll();
    }
}
