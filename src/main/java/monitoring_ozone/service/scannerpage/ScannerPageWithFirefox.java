package monitoring_ozone.service.scannerpage;

import monitoring_ozone.constants.EmptyProductName;
import monitoring_ozone.constants.XPathConstants;
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
        String name = getWebelement(XPathConstants.TITLES, driver).getText();
        if (EmptyProductName.ALL.contains(name)) {
            product.setName(name);
            product.setPrice(0);
            product.setUrl(url);
        } else {
            product.setName(name);
            product.setPrice(StringToInteger.parseInt(getWebelement(XPathConstants.PRICES, driver).getText()));
            product.setUrl(url);
        }

        driver.close();
        return product;
    }

    private WebElement getWebelement(String[] elements,
                                     FirefoxDriver driver) {
        WebElement webElement = null;
        for (String element : elements) {
            try {
                webElement = driver.findElement(By.xpath(element));
                break;
            } catch (NoSuchElementException ignored) {
            }
        }
        return webElement;
    }
}
