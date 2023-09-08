package monitoring_ozone.MVC.controller;

import monitoring_ozone.model.Product;
import monitoring_ozone.service.ProductService;
import monitoring_ozone.service.ScannerPageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductsController {

    private final ProductService productService;
    private final ScannerPageService scannerPageService;

    public ProductsController(ProductService productService, ScannerPageService scannerPageService) {
        this.productService = productService;
        this.scannerPageService = scannerPageService;
    }

    @GetMapping("/add")
    public String startPage() {
        return "addProduct";
    }

    @PostMapping("/add")
    public String getAndSaveProduct(@ModelAttribute("productForm") Product prod) {
        Product product = scannerPageService.getProduct(prod.getUrl());
        productService.addProduct(product);
        return "redirect:/products/add";
    }

    @GetMapping("/all")
    public String allProducts(Model model) {
        model.addAttribute("products", productService.getAll());
        return "allProducts";
    }
}
