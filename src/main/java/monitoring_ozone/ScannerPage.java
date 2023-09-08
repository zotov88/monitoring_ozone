package monitoring_ozone;

import monitoring_ozone.constants.XPathConstants;
import monitoring_ozone.model.Product;
import monitoring_ozone.model.TurningProduct;
import monitoring_ozone.utils.StringToInteger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.stereotype.Component;

@Component
public class ScannerPage implements TurningProduct {

    @Override
    public Product getProduct(String url) {
        FirefoxDriver driver = new FirefoxDriver();
        driver.get(url);
        Product product = new Product();
        product.setName(getWebelement(XPathConstants.titles, driver).getText());
        product.setPrice(StringToInteger.parseInt(getWebelement(XPathConstants.prices, driver).getText()));
        product.setUrl(url);
        driver.close();
        return product;
    }

    private WebElement getWebelement(String[] elements, FirefoxDriver driver) {
        WebElement webElement = null;
        for (String price : elements) {
            try {
                webElement = driver.findElement(By.cssSelector(price));
            } catch (NoSuchElementException ignored) {
            }
        }
        return webElement;
    }
}
