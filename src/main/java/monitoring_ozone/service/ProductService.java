package monitoring_ozone.service;

import monitoring_ozone.model.Product;
import monitoring_ozone.repository.ProductRepository;
import monitoring_ozone.repository.UserRepository;
import monitoring_ozone.service.notifications.SenderNotifications;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final UserRepository userRepository;
    private final ScannerPageService scannerPageService;
    private final StoryService storyService;
    private final SenderNotifications notifications;

    public ProductService(ProductRepository repository,
                          UserRepository userRepository,
                          ScannerPageService scannerPageService,
                          StoryService storyService,
                          SenderNotifications notifications) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.scannerPageService = scannerPageService;
        this.storyService = storyService;
        this.notifications = notifications;
    }

    public void create(Product product) {
        repository.save(product);
    }

    public List<Product> getAllByUserId(Long id) {
        return repository.findAllByUserIdN(id);
    }

    public void update(Product product) {
        create(product);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Product getOne(Long id) {
        return repository.getReferenceById(id);
    }

    public void checkProductsAllUsers() {
        for (Long id : repository.getDistinctByUserId()) {
            checkProductsOneUser(id);
        }
    }

    public void checkProductsOneUser(Long id) {
        List<Product> productList = getAllByUserId(id);
        Map<Product, Integer> cheaperProducts = new HashMap<>();
        for (Product product : productList) {
            int previousPrice = product.getExpectedPrice() == null ? product.getPrice() : product.getExpectedPrice();
            Product tmpProduct = scannerPageService.getProduct(product.getUrl());
            if (!Objects.equals(previousPrice, tmpProduct.getPrice())) {
                storyService.create(product, tmpProduct.getPrice());
                product.setPrice(tmpProduct.getPrice());
                update(product);
                if (tmpProduct.getPrice() < previousPrice) {
                    cheaperProducts.put(product, previousPrice - tmpProduct.getPrice());
                }
            }
        }
        if (!cheaperProducts.isEmpty()) {
            notifications.sendAll(userRepository.getById(id), createMessage(cheaperProducts));
        }
    }

    private String createMessage(Map<Product, Integer> changesProducts) {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Product product : changesProducts.keySet()) {
            sb.append(i++).append(". ").append(product.getName())
                    .append(" подешевел на ").append(changesProducts.get(product))
                    .append(" рублей. Текущая цена - ").append(product.getPrice()).append(" рублей\n");
        }
        return sb.toString();
    }

}
