package monitoring_ozone.service;

import monitoring_ozone.model.Product;
import monitoring_ozone.repository.ProductRepository;
import monitoring_ozone.repository.UserRepository;
import monitoring_ozone.service.notifications.SenderNotifications;
import org.springframework.stereotype.Service;

import java.util.*;

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
            int previousPrice = product.getPrice();
            Product updProduct = scannerPageService.getProduct(product.getUrl());
            if (!Objects.equals(previousPrice, updProduct.getPrice())) {
                storyService.create(product, updProduct.getPrice());
                product.setPrice(updProduct.getPrice());
                update(product);
            }
            priceComparison(product, updProduct, cheaperProducts);
        }
        if (!cheaperProducts.isEmpty()) {
            notifications.sendAll(userRepository.getReferenceById(id), createMessage(cheaperProducts));
        }
    }

    private void priceComparison(final Product product,
                                 final Product updProduct,
                                 final Map<Product, Integer> cheaperProducts) {
        int targetPrice = product.getExpectedPrice() != null ? product.getExpectedPrice() : product.getPrice();
        if (updProduct.getPrice() < targetPrice) {
            cheaperProducts.put(product, product.getPrice() - updProduct.getPrice());
        }
    }

    private String createMessage(final Map<Product, Integer> changesProducts) {
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
