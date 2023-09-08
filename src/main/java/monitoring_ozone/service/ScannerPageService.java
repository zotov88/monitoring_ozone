package monitoring_ozone.service;

import monitoring_ozone.ScannerPage;
import monitoring_ozone.model.Product;
import org.springframework.stereotype.Service;

@Service
public class ScannerPageService {

    private final ScannerPage scannerPage;

    public ScannerPageService(ScannerPage scannerPage) {
        this.scannerPage = scannerPage;
    }

    public Product getProduct(String url) {
        return scannerPage.getProduct(url);
    }
}
