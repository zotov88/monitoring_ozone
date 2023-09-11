package monitoring_ozone.MVC.controller;

import monitoring_ozone.model.Product;
import monitoring_ozone.service.ProductService;
import monitoring_ozone.service.ScannerPageService;
import monitoring_ozone.service.StoryService;
import monitoring_ozone.service.notifications.SenderNotifications;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ScannerPageService scannerPageService;
    private final StoryService storyService;
    private final SenderNotifications notifications;

    public ProductController(ProductService productService,
                             ScannerPageService scannerPageService,
                             StoryService storyService, SenderNotifications notifications) {
        this.productService = productService;
        this.scannerPageService = scannerPageService;
        this.storyService = storyService;
        this.notifications = notifications;
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
        Map<Product, Integer> cheaperProducts = new HashMap<>();
        for (Product product : productList) {
            int previousPrice = product.getPrice();
            Product tmpProduct = scannerPageService.getProduct(product.getUrl());
            if (!Objects.equals(product.getPrice(), tmpProduct.getPrice())) {
                storyService.create(product, tmpProduct.getPrice());
                product.setPrice(tmpProduct.getPrice());
                productService.update(product);
                if (tmpProduct.getPrice() < previousPrice) {
                    cheaperProducts.put(product, previousPrice - tmpProduct.getPrice());
                }
            }
        }
        if (!cheaperProducts.isEmpty()) {
            notifications.sendAll(createMessage(cheaperProducts));
        }
        return "redirect:/products/all";
    }

    private String createMessage(Map<Product, Integer> changesProducts) {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Product product : changesProducts.keySet()) {
            sb.append(i++).append(". ").append(product.getName())
                    .append(" подешевел на ").append(changesProducts.get(product))
                    .append(" рублей. Текущая цена - ").append(product.getPrice()).append(" рублей\n");
        }
        return sb.toString();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/products/all";
    }
}
