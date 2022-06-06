package site.golets.selenium.bot.service;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.golets.selenium.bot.model.Order;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@SpringBootTest(properties = "http.parser.seleniumUrl=http://localhost:4444/wd/hub")
public class OrdersPageServiceTest extends Assertions {

    @Autowired
    private OrdersPageService ordersPageService;


    @Autowired
    private LoginPageService loginPageService;


    private WebDriver webDriver;

//    @Test
    public void loginTest() throws FileNotFoundException {
/*

        ordersPageService.visitOrdersPage();
        loginPageService.verifyLogin();

        PrintWriter out = new PrintWriter("result.html");
        out.println(webDriver.getPageSource());
*/

    }



 //   @Test
    public void parseOrderTest() throws IOException, InterruptedException {

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("file:///home/andriig/IdeaProjects/order-search/selenium-bot/order-pages/several-orders.html");
        Map<String, Order> orderMap = ordersPageService.parseOrderMap(driver);

        assertEquals(2, orderMap.size());
        assertEquals("The Scottish Highlander", orderMap.get("#70339").getProductTitle());
        assertEquals("The Equestrian Lady", orderMap.get("#70297").getProductTitle());
        driver.close();
    }

    public static void main(String[] args) {

        System.out.println(LocalDateTime.now().atZone(ZoneId.of("Europe/Kiev")).format(DateTimeFormatter.ofPattern("hh:mm")));

    }

}
