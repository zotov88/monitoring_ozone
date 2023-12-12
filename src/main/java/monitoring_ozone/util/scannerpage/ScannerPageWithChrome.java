package monitoring_ozone.util.scannerpage;

import monitoring_ozone.constants.EmptyProductName;
import monitoring_ozone.constants.Markets;
import monitoring_ozone.constants.XPathConstants;
import monitoring_ozone.model.Market;
import monitoring_ozone.model.Product;
import monitoring_ozone.util.atoi.StringToInteger;
import monitoring_ozone.util.chromedriver.ChromeDriverBuilder;
import monitoring_ozone.util.chromedriver.UndetectedChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import static monitoring_ozone.constants.Errors.Message.ROBOT_CHECK;

@Component
@Primary
public class ScannerPageWithChrome implements TurningProduct {

    @Override
    public Product getProduct(final String url) {
        ChromeDriver driver = getChromeDriver(url);
//        driver.get(url);
        Product product = new Product();
        String name = getWebelement(XPathConstants.TITLES, driver).getText();
        product.setName(name);
        product.setUrl(url);
        product.setMarket(getMarketByUrl(url));
        if (EmptyProductName.ALL.contains(name)) {
            product.setPrice(0);
        } else {
            product.setPrice(StringToInteger.parseInt(getWebelement(XPathConstants.PRICES, driver).getText()));
        }
        driver.close();
        return product;
    }

    private static ChromeDriver getChromeDriver(String url) {
        String driverHome = "/home/user/.m2/repository/org/seleniumhq/selenium/selenium-chrome-driver";
//        options.addArguments("--remote-allow-origins=*", "--disable-notifications", "--disable-blink-features=AutomationControlled", "--disable-user-media-security");
        ChromeDriver driver = new ChromeDriverBuilder().build(driverHome);
        driver.get(url);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        while (driver.getPageSource().contains(ROBOT_CHECK)) {
            driver.close();
            driver = new ChromeDriverBuilder().build(url);
            driver.get(url);
        }
        return driver;
    }

    private Market getMarketByUrl(final String url) {
        Market market = new Market();
        market.setId(url.contains(Markets.OZONE) ? 1L : 2L);
        return market;
    }

    private WebElement getWebelement(String[] elements, ChromeDriver driver) {
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
