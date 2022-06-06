package site.golets.selenium.bot.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.golets.selenium.bot.model.Order;
import site.golets.selenium.bot.model.PreferedOrders;
import site.golets.selenium.bot.properties.HttpParserProperties;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

@Service
@Slf4j
public class OrdersPageService {

    public static final String ORDERS_TABLE_BODY_TR = "//*[@id=\"table-container\"]/div[1]/div/table/tbody/tr";
    public static final String NEXT_BUTTON = "//*[@id=\"table-container\"]/div[2]/div[2]/button[2]";
    public static final String NAME_CELL_PATH = "//*[@id=\"table-container\"]/div[1]/div/table/tbody/tr[%d]/td[1]/div/div[2]/div/div[1]/div[1]";
    public static final String PRODUCT_CELL_PATH = "//*[@id=\"table-container\"]/div[1]/div/table/tbody/tr[%d]/td[3]/div/div/div[1]/div[1]";
    public static final String PHOTO_NOT_OK = "//*[@id=\"table-container\"]/div[1]/div/table/tbody/tr[%d]/td[6]/div/div/div/div";


    private final HttpParserProperties properties;

    @Getter
    private WebDriver driver;

    @Getter
    private final Map<String, Order> ordersMap = new ConcurrentHashMap<>();

    @Getter
    private final Queue<Order> newOrders = new ConcurrentLinkedDeque<>();

    @Autowired
    public OrdersPageService(HttpParserProperties properties) {
        this.properties = properties;
    }

    public void visitOrdersPage() {
        driver.get(properties.getOrderPageUrl());
        driver.manage().window().maximize();
        try {
            Thread.sleep(properties.getDelayAfterOrderPageLoad());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void parsePageForOrders() {
        Map<String, Order> orders = parseOrderMap(driver);
        orders.keySet().stream().filter(id -> !ordersMap.containsKey(id)).forEach(id -> newOrders.offer(orders.get(id)));
        ordersMap.clear();
        ordersMap.putAll(orders);
    }

    public Map<String, Order> parseOrderMap(WebDriver driver) {
        Map<String, Order> orderMap = new HashMap<>();

        boolean nextPagePresent = true;
        while (nextPagePresent) {
            WebElement nextButton = null;
            try {
                nextButton = driver.findElement(By.xpath(NEXT_BUTTON));
                nextPagePresent = nextButton.isEnabled();
            } catch (Exception e) {
                log.warn("NEXT_BUTTON Parse Error", e);
                nextPagePresent = false;
            }

            List<WebElement> tableRows;
            try {
                tableRows = driver.findElements(By.xpath(ORDERS_TABLE_BODY_TR));
            } catch (Exception e) {
                log.warn("ORDERS_TABLE_BODY_TR Parse Error", e);
                continue;
            }

            for (int i = 1; i <= tableRows.size(); i++) {
                Order order = new Order();
                try {
                    order.setName(driver.findElement(By.xpath(String.format(NAME_CELL_PATH, i))).getText());
                    order.setProductTitle(driver.findElement(By.xpath(String.format(PRODUCT_CELL_PATH, i))).getText());
                    order.setPhotoNotOk(driver.findElement(By.xpath(String.format(PHOTO_NOT_OK, i))).getText());

                    log.info(order.toString());

                    if (!order.getProductTitle().startsWith("Get a digital file") &&
                            !order.getProductTitle().startsWith("Get your canvas")) {
                        if (PreferedOrders.threeHeadsOrders.contains(order.getProductTitle())) {
                            order.setHeads(3);
                        }
                        if (PreferedOrders.fourHeadsOrders.contains(order.getProductTitle())) {
                            order.setHeads(4);
                        }
                        log.info(order.toString());
                        orderMap.put(order.getName(), order);
                    }
                } catch (Exception e) {
                    log.warn("CELL_PATH Parse Error", e);
                }
            }
            if (nextPagePresent && nextButton != null) {
                nextButton.click();
            }
        }
        return orderMap;
    }

    public void registerWebDriver() {
        try {
            WebDriver webDriver = new RemoteWebDriver(new URL(this.properties.getSeleniumUrl()), new ChromeOptions());
            log.info("WebDriver Created");
            driver = webDriver;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Selenium register error : " + e.getMessage(), e);
        }

    }

}
