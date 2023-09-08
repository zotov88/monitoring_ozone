package monitoring_ozone.MVC.controller;

import monitoring_ozone.model.Product;
import monitoring_ozone.service.ProductService;
import monitoring_ozone.service.ScannerPageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    private final ProductService productService;
    private final ScannerPageService scannerPageService;

    public MainController(ProductService productService, ScannerPageService scannerPageService) {
        this.productService = productService;
        this.scannerPageService = scannerPageService;
    }

    @GetMapping("")
    public String startPage() {
        return "startPage";
    }

    @PostMapping("")
    public String getAndSaveProduct(@ModelAttribute("productForm") Product prod) {

        String url = prod.getUrl();
        Product product = scannerPageService.getProduct(url);
        productService.addProduct(product);
        return "redirect:/";
    }
}
