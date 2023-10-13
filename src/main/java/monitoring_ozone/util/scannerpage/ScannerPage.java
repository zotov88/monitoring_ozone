package monitoring_ozone.util.scannerpage;

import monitoring_ozone.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ScannerPage {

    private final TurningProduct turningProduct;

    public ScannerPage(TurningProduct turningProduct) {
        this.turningProduct = turningProduct;
    }

    public Product scanPage(String url) {
        return turningProduct.getProduct(url);
    }
}
