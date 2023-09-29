package monitoring_ozone.service.scannerpage;

import monitoring_ozone.constants.CSSSelectorConstants;
import monitoring_ozone.model.Product;
import monitoring_ozone.utils.StringToInteger;
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
        product.setName(getWebelement(CSSSelectorConstants.titles, driver).getText());
        product.setPrice(StringToInteger.parseInt(getWebelement(CSSSelectorConstants.prices, driver).getText()));
        product.setUrl(url);
        driver.close();
        return product;
    }

    private WebElement getWebelement(String[] elements, FirefoxDriver driver) {
        WebElement webElement = null;
        for (String element : elements) {
            try {
                System.out.println("++++++++++++++++++++++++"+element);
                webElement = driver.findElement(By.cssSelector(element));
                break;
            } catch (NoSuchElementException ignored) {
            }
        }
        return webElement;
    }
}
