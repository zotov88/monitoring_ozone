package monitoring_ozone.MVC.controller;

import monitoring_ozone.model.Product;
import monitoring_ozone.service.ProductService;
import monitoring_ozone.service.ScannerPageService;
import monitoring_ozone.service.StoryService;
import monitoring_ozone.service.UserService;
import monitoring_ozone.service.notifications.SenderNotifications;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final UserService userService;
    private final ScannerPageService scannerPageService;
    private final StoryService storyService;
    private final SenderNotifications notifications;

    public ProductController(ProductService productService,
                             UserService userService,
                             ScannerPageService scannerPageService,
                             StoryService storyService,
                             SenderNotifications notifications) {
        this.productService = productService;
        this.userService = userService;
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
        product.setUser(userService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
        productService.create(product);
        storyService.create(product, product.getPrice());
        return "redirect:/products/add";
    }

    @GetMapping("/all/{id}")
    public String allProducts(@PathVariable Long id,
                              Model model) {
        model.addAttribute("products", productService.getAllByUserId(id));
        return "allProducts";
    }

    @PostMapping("/all/{id}")
    public String updateListProducts(@PathVariable Long id) {
        productService.checkProductsOneUser(id);
        return "redirect:/products/all/{id}";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/products/all";
    }
}
