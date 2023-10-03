package monitoring_ozone.service.scannerpage;

import monitoring_ozone.constants.CSSSelectorConstants;
import monitoring_ozone.model.Product;
import monitoring_ozone.utils.StringToInteger;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.stereotype.Component;

@Component
public class ScannerPageWithFirefox implements TurningProduct {

    @Override
    public Product getProduct(String url) {
        FirefoxDriver driver = new FirefoxDriver();
        driver.get(url);
        Product product = new Product();
        String name = getWebelement(CSSSelectorConstants.titles, driver).getText();
        product.setName(name.length() > 50 ? name.substring(0, 50) : name);
        product.setPrice(StringToInteger.parseInt(getWebelement(CSSSelectorConstants.prices, driver).getText()));
        product.setUrl(url);
        driver.close();
        return product;
    }

    private WebElement getWebelement(String[] elements, FirefoxDriver driver) {
        WebElement webElement = null;
        for (String element : elements) {
            try {
                webElement = driver.findElement(By.cssSelector(element));
                break;
            } catch (NoSuchElementException ignored) {
            }
        }
        return webElement;
    }
}
