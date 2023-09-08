package monitoring_ozone;

import monitoring_ozone.constants.XPathConstants;
import monitoring_ozone.model.Product;
import monitoring_ozone.model.TurningProduct;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.stereotype.Component;

@Component
public class ScannerPage implements TurningProduct {

//    private final String url;
//
//    public ScannerPage(String url) {
//        this.url = url;
//    }

    @Override
    public Product getProduct(String url) {
//        String url = "https://www.ozon.ru/product/robot-pylesos-xiaomi-mijia-robot-vacuum-cleaner-mop-lds-black-suhaya-vlazhnaya-uborka-upravlenie-s-861610769/?sh=dqpNhj0rTQ";
        Product product = new Product();
//        ChromeOptions options = new ChromeOptions().addArguments("--remote-allow-origins=*");
        FirefoxDriver driver = new FirefoxDriver();
//            driver.manage().window().setSize(new Dimension(1, 1));
        driver.get(url);
        WebElement element = driver.findElement(By.xpath(XPathConstants.title));

        product.setName(element.getText());
        product.setUrl(url);
        driver.close();

        return product;
    }
}
