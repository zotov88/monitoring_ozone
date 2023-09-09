package monitoring_ozone.MVC.controller;

import monitoring_ozone.model.Product;
import monitoring_ozone.model.Story;
import monitoring_ozone.service.ProductService;
import monitoring_ozone.service.ScannerPageService;
import monitoring_ozone.service.StoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductsController {

    private final ProductService productService;
    private final ScannerPageService scannerPageService;
    private final StoryService storyService;

    public ProductsController(ProductService productService,
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
        productService.addProduct(product);
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
            Story story = new Story();
            story.setProduct(product);
            story.setPrice(tmpProduct.getPrice());
            story.setDate(LocalDate.now());
            storyService.add(story);
        }
        return "redirect:/products/all";
    }
}
