package monitoring_ozone.util.scannerpage;

import monitoring_ozone.model.Product;
import monitoring_ozone.util.notification.NotificationProxy;
import org.springframework.stereotype.Component;

@Component
public class ScannerPage {

    private final TurningProduct turningProduct;
    private final NotificationProxy notificationProxy;

    public ScannerPage(TurningProduct turningProduct,
                       NotificationProxy notificationProxy) {
        this.turningProduct = turningProduct;
        this.notificationProxy = notificationProxy;
    }

    public Product scanPage(String url) {
        Product product = turningProduct.getProduct(url);
        if (product == null) {
            notificationProxy.sendToAdmin("Ошибка при сохранении товара " + url);
        }
        return product;
    }
}
