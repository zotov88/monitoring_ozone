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
    //временно
    private final UserRepository userRepository;

    public ProductService(ProductRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public void create(Product product) {
        //временно
        User user = new User();
        user.setName("Ivan");
        Role role = new Role();
        role.setId(1L);
        user.setRole(role);
        user.setLogin("login");
        user.setPassword("1234");
        user.setTgBotToken("123455");
        user.setTgChatId(123L);
        userRepository.save(user);
        product.setUser(user);
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
