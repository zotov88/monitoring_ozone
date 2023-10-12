package monitoring_ozone.service;

import jakarta.transaction.Transactional;
import monitoring_ozone.model.Product;
import monitoring_ozone.repository.ProductRepository;
import monitoring_ozone.service.notifications.SenderNotifications;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final UserService userService;
    private final StoryService storyService;
    private final ScannerPageService scannerPageService;
    private final SenderNotifications notifications;

    public ProductService(ProductRepository productRepository,
                          UserService userService,
                          StoryService storyService,
                          ScannerPageService scannerPageService,
                          SenderNotifications notifications) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.storyService = storyService;
        this.scannerPageService = scannerPageService;
        this.notifications = notifications;
    }

    public void create(Product product) {
        productRepository.save(product);
    }

    public List<Product> getAllByUserIdSortedByName(Long id) {
        return productRepository.findAllByUserIdSortedByName(id);
    }

    public void update(Product product) {
        create(product);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public Product getOne(Long id) {
        return productRepository.getReferenceById(id);
    }

    public void checkProductsAllUsers() {
        for (Long id : productRepository.getDistinctByUserId()) {
            checkProductsOneUser(getAllByUserIdSortedByName(id), id);
        }
    }

    public void checkProductsOneUser(List<Product> products, final Long userId) {
        Map<Product, Integer> cheaperProducts = new HashMap<>();
        for (Product product : products) {
            int previousPrice = product.getPrice();
            Product updProduct = scannerPageService.getProduct(product.getUrl());
            if (!Objects.equals(previousPrice, updProduct.getPrice())) {
                storyService.create(product, updProduct.getPrice());
                product.setPrice(updProduct.getPrice());
                product.setMinPrice(storyService.getMinPriceByProductId(product.getId()));
                product.setMarket(updProduct.getMarket());
                update(product);
            }
            priceComparison(product, updProduct, previousPrice, cheaperProducts);
        }
        if (!cheaperProducts.isEmpty()) {
            notifications.sendAll(userService.getById(userId), createMessage(cheaperProducts));
        }
    }

    public void checkProductUser(final Long productId, final Long userId) {
        checkProductsOneUser(new ArrayList<>(List.of(productRepository.getReferenceById(productId))), userId);
    }

    private void priceComparison(final Product product,
                                 final Product updProduct,
                                 final Integer previousPrice,
                                 final Map<Product, Integer> cheaperProducts) {
        int targetPrice = product.getExpectedPrice() != null ? product.getExpectedPrice() : previousPrice;
        if (updProduct.getPrice() < targetPrice && product.getPrice() != 0) {
            cheaperProducts.put(product, previousPrice - updProduct.getPrice());
        }
    }

    private String createMessage(final Map<Product, Integer> changesProducts) {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Product product : changesProducts.keySet()) {
            sb.append(i++).append(". ")
                    .append(product.getName()).append(" подешевел на ").append(changesProducts.get(product)).append(" р.\n").
                    append("Текущая цена: ").append(product.getPrice()).append(" р.\n").
                    append("Минимальная цена: ").append(product.getMinPrice()).append(" р.\n\n");
        }
        return sb.toString();
    }
}
