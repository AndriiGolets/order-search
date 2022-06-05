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

//@SpringBootTest
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

//    @Test
    public void getOrderPage() throws IOException, InterruptedException {

        ordersPageService.visitOrdersPage();
        loginPageService.verifyLogin();

//        WebDriverManager.chromedriver().setup();
//        WebDriver driver = new ChromeDriver();
//        driver.get("file:///home/andriig/IdeaProjects/selenium-bot/order-pages/order-page.html");

        Map<String, Order> orderMap = ordersPageService.parseOrderMap(webDriver);

        orderMap.values().stream().findFirst().get().getTableRow().click();

        Thread.sleep(3000);

        assertTrue(webDriver.getTitle().startsWith("#"));

        WebElement editButton = webDriver.findElement(By.xpath("//*[@id=\"page\"]/div/div[3]/div/div/div/div/div/div[2]/div[3]/button"));
        editButton.click();

        Thread.sleep(3000);

        PrintWriter out = new PrintWriter("result.html");
        out.println(webDriver.getPageSource());
        webDriver.close();
    }

//    @Test
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
