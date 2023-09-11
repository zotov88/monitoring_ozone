package monitoring_ozone.config;

import monitoring_ozone.MVC.controller.ProductController;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    private final ProductController productController;

    public Scheduler(ProductController productController) {
        this.productController = productController;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void updateListOfProducts() {
        productController.updateListProducts();
    }
}
