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
import org.springframework.validation.BindingResult;
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

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         Model model) {
        model.addAttribute("productForm", productService.getOne(id));
        return "updateProduct";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("productForm") Product productUpdated,
                         BindingResult bindingResult) {
        Product foundProduct = productService.getOne(productUpdated.getId());
        int expectedPrice = 0;
        if (productUpdated.getExpectedPrice() != null) {
            try {
                expectedPrice = productUpdated.getExpectedPrice();
            } catch (Exception e) {
                bindingResult.rejectValue("expectedPrice", "error.expectedPrice", "Введите число");
                return "updateProduct";
            }
        }
        foundProduct.setName(productUpdated.getName());
        foundProduct.setExpectedPrice(expectedPrice == 0 ? null : expectedPrice);
        productService.update(foundProduct);
        return "redirect:/products/all/" +
                userService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
    }

}
