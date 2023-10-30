package monitoring_ozone.util.scannerpage;

import monitoring_ozone.constants.EmptyProductName;
import monitoring_ozone.constants.Markets;
import monitoring_ozone.constants.XPathConstants;
import monitoring_ozone.model.Market;
import monitoring_ozone.model.Product;
import monitoring_ozone.util.atoi.StringToInteger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class ScannerPageWithFirefox implements TurningProduct {

    @Override
    public Product getProduct(final String url) {
        FirefoxDriver driver = new FirefoxDriver();
        driver.get(url);
        Product product = new Product();
        WebElement webElement = getWebelement(XPathConstants.TITLES, driver);
        if (webElement == null) {
            driver.close();
            return null;
        }
        product.setName(webElement.getText());
        product.setUrl(url);
        product.setMarket(getMarketByUrl(url));
        if (EmptyProductName.ALL.contains(webElement.getText())) {
            product.setPrice(0);
        } else {
            webElement = getWebelement(XPathConstants.PRICES, driver);
            if (webElement == null) {
                driver.close();
                return null;
            }
            product.setPrice(StringToInteger.parseInt(webElement.getText()));
        }

        driver.close();
        return product;
    }

    private Market getMarketByUrl(final String url) {
        Market market = new Market();
        market.setId(url.contains(Markets.OZONE) ? 1L : 2L);
        return market;
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
