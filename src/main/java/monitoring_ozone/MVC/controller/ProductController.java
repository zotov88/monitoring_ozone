package monitoring_ozone.MVC.controller;

import monitoring_ozone.model.Product;
import monitoring_ozone.service.ProductService;
import monitoring_ozone.service.ScannerPageService;
import monitoring_ozone.service.StoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ScannerPageService scannerPageService;
    private final StoryService storyService;

    public ProductController(ProductService productService,
                             ScannerPageService scannerPageService,
                             StoryService storyService) {
        this.productService = productService;
        this.scannerPageService = scannerPageService;
        this.storyService = storyService;
    }

    @GetMapping("/add")
    public String add() {
        return "addProduct";
    }

    @PostMapping("/add")
    public String getAndSaveProduct(@ModelAttribute("productForm") Product prod) {
        Product product = scannerPageService.getProduct(prod.getUrl());
        productService.create(product);
        storyService.create(product, product.getPrice());
        return "redirect:/products/add";
    }

    @GetMapping("/all")
    public String allProducts(Model model) {
        model.addAttribute("products", productService.getAll());
        return "allProducts";
    }

    @PostMapping("/all")
    public String updateListProducts() {
        List<Product> productList = productService.getAll();
        for (Product product : productList) {
            Product tmpProduct = scannerPageService.getProduct(product.getUrl());
            if (!Objects.equals(product.getPrice(), tmpProduct.getPrice())) {
                storyService.create(product, tmpProduct.getPrice());
                product.setPrice(tmpProduct.getPrice());
                productService.update(product);
            }
        }
        return "redirect:/products/all";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/products/all";
    }
}
