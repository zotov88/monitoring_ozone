package monitoring_ozone.MVC.controller;

import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import monitoring_ozone.model.Product;
import monitoring_ozone.service.ProductService;
import monitoring_ozone.service.StoryService;
import monitoring_ozone.service.UserService;
import monitoring_ozone.util.checkaccess.CheckAccess;
import monitoring_ozone.util.scannerpage.ScannerPage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static monitoring_ozone.constants.Route.*;

@Controller
@RequestMapping(PRODUCT)
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final UserService userService;
    private final ScannerPage scannerPage;
    private final StoryService storyService;

    @PostMapping(ADD)
    public String getAndSaveProduct(@ModelAttribute("productForm") Product prod) {
        Product product = scannerPage.scanPage(prod.getUrl());
        if (product == null) {
            return "product/failureAdd";
        }
        product.setUser(userService.getByLogin(SecurityContextHolder.getContext().getAuthentication().getName()));
        product.setMinPrice(product.getPrice());
        productService.create(product);
        storyService.create(product, product.getPrice());
        return "redirect:/products/all/" + getId();
    }

    @GetMapping(ALL + "/{userId}")
    public String allProducts(@PathVariable Long userId,
                              Model model) throws AuthException {
        CheckAccess.checkAccess(userId);
        model.addAttribute("products", productService.getAllByUserIdSortedByName(userId));
        return "product/allProducts";
    }

    @PostMapping(ALL + "/{userId}")
    public String updateListProducts(@PathVariable Long userId) {
        productService.checkProductsOneUser(productService.getAllByUserIdSortedByName(userId), userId);
        return "redirect:/products/all/{userId}";
    }

    @GetMapping(ALL + "/{userId}/{productId}")
    public String updateOneProduct(@PathVariable Long productId,
                                   @PathVariable Long userId) {
        productService.checkProductUser(productId, userId);
        return "redirect:/products/all/{userId}";
    }

    @GetMapping(DELETE + "/{productId}")
    public String delete(@PathVariable Long productId) {
        productService.delete(productId);
        return "redirect:/products/all/" + getId();
    }

    @GetMapping(UPDATE + "/{productId}")
    public String update(@PathVariable Long productId,
                         Model model) {
        model.addAttribute("productForm", productService.getOne(productId));
        return "product/updateProduct";
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
                return "product/updateProduct";
            }
        }
        foundProduct.setName(productUpdated.getName());
        foundProduct.setExpectedPrice(expectedPrice == 0 ? null : expectedPrice);
        productService.update(foundProduct);
        return "redirect:/products/all/" + getId();
    }

    private Long getId() {
        return userService.getByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
    }
}
