package monitoring_ozone.config;

import monitoring_ozone.service.ProductService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    private final ProductService productService;

    public Scheduler(ProductService productService) {
        this.productService = productService;
    }

    @Scheduled(cron = "0 0 9-22/3 * * *")
    public void updateListOfProducts() {
        productService.checkProductsAllUsers();
    }
}
