package monitoring_ozone.service;

import monitoring_ozone.service.scannerpage.ScannerPageWithFirefox;
import monitoring_ozone.model.Product;
import org.springframework.stereotype.Service;

@Service
public class ScannerPageService {

    private final ScannerPageWithFirefox scannerPageWithFirefox;

    public ScannerPageService(ScannerPageWithFirefox scannerPageWithFirefox) {
        this.scannerPageWithFirefox = scannerPageWithFirefox;
    }

    public Product getProduct(String url) {
        return scannerPageWithFirefox.getProduct(url);
    }
}
