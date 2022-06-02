package site.golets.selenium.bot.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.golets.selenium.bot.model.Order;
import site.golets.selenium.bot.properties.HttpParserProperties;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

@Service
@Slf4j
public class OrdersPageService {

    public static final String ORDERS_TABLE_BODY_TR = "//*[@id=\"table-container\"]/div[1]/div/table/tbody/tr";
    public static final By BY_DATA_TITLE = By.cssSelector("div[data-title]");

    private final HttpParserProperties properties;
    private final WebDriver driver;

    private final Map<String, Order> ordersMap = new ConcurrentHashMap<>();

    @Getter
    private final Queue<Order> newOrders = new ConcurrentLinkedDeque<>();

    @Autowired
    public OrdersPageService(HttpParserProperties properties, WebDriver driver) {
        this.properties = properties;
        this.driver = driver;
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
        orders.keySet().stream().filter(o -> !ordersMap.containsKey(o)).forEach(o -> newOrders.offer(orders.get(o)));
        ordersMap.putAll(orders);
    }

    public Map<String, Order> parseOrderMap(WebDriver driver) {
        List<WebElement> tableRows = driver.findElements(By.xpath(ORDERS_TABLE_BODY_TR));

        Map<String, Order> orderMap = new HashMap<>();
        for (WebElement tableRow : tableRows) {
            List<WebElement> orderCells = tableRow.findElements(By.xpath(".//td"));
            Order order = new Order();
            for (WebElement orderCell : orderCells) {
                if (orderCell.getAttribute("class").contains("name")) {
                    order.setName(orderCell.findElement(BY_DATA_TITLE).getText());
                }
                if (orderCell.getAttribute("class").contains("artist")) {
                    order.setArtist(orderCell.findElement(BY_DATA_TITLE).getText());
                }
                if (orderCell.getAttribute("class").contains("product_title")) {
                    order.setProductTitle(orderCell.findElement(BY_DATA_TITLE).getText());
                }
                if (orderCell.getAttribute("class").contains("date")) {
                    order.setDate(orderCell.findElement(BY_DATA_TITLE).getText());
                }
                if (orderCell.getAttribute("class").contains("product_variant")) {
                    order.setProductVariant(orderCell.findElement(BY_DATA_TITLE).getText());
                }
                if (orderCell.getAttribute("class").contains("photo_not_ok")) {
                    order.setPhotoNotOk(orderCell.findElement(By.xpath(".//*[contains(@class, 'stk-text')]")).getText());
                }
            }
            log.info(order.toString());
            if (!order.getProductTitle().equals("Get a digital file of your portrait")) {
                order.setTableRow(tableRow);
                orderMap.put(order.getName(), order);
            }
        }
        return orderMap;
    }

}
