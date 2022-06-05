package site.golets.selenium.bot.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
    public static final By BY_DATA_TITLE = By.cssSelector("div[data-title]");

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
            WebElement nextButton = driver.findElement(By.xpath(NEXT_BUTTON));
            nextPagePresent = nextButton.isEnabled();
            List<WebElement> tableRows;
            try {
                tableRows = driver.findElements(By.xpath(ORDERS_TABLE_BODY_TR));
            } catch (Exception e) {
                continue;
            }
            for (WebElement tableRow : tableRows) {
                List<WebElement> orderCells = tableRow.findElements(By.xpath(".//td"));
                Order order = new Order();
                for (WebElement orderCell : orderCells) {
                    try {
                        if (orderCell.getAttribute("class").contains("name")) {
                            order.setName(orderCell.findElement(BY_DATA_TITLE).getText());
                        }
                    } catch (Exception e) {
                        order.setName("");
                    }
                    try {
                        if (orderCell.getAttribute("class").contains("artist")) {
                            order.setArtist(orderCell.findElement(BY_DATA_TITLE).getText());
                        }
                    } catch (Exception e) {
                        order.setArtist("");
                    }
                    try {
                        if (orderCell.getAttribute("class").contains("product_title")) {
                            order.setProductTitle(orderCell.findElement(BY_DATA_TITLE).getText());
                        }
                    } catch (Exception e) {
                        order.setProductTitle("");
                    }
                    try {
                        if (orderCell.getAttribute("class").contains("date")) {
                            order.setDate(orderCell.findElement(BY_DATA_TITLE).getText());
                        }
                    } catch (Exception e) {
                        order.setDate("");
                    }
                    try {
                        if (orderCell.getAttribute("class").contains("product_variant")) {
                            order.setProductVariant(orderCell.findElement(BY_DATA_TITLE).getText());
                        }
                    } catch (Exception e) {
                        order.setProductVariant("");
                    }
                    try {
                        if (orderCell.getAttribute("class").contains("photo_not_ok")) {
                            order.setPhotoNotOk(orderCell.findElement(By.xpath(".//*[contains(@class, 'stk-text')]")).getText());
                        }
                    } catch (Exception e) {
                        order.setPhotoNotOk("");
                    }
                }
                log.info(order.toString());
                if (!order.getProductTitle().startsWith("Get a digital file") &&
                        !order.getProductTitle().startsWith("Get your canvas")) {
                    order.setTableRow(tableRow);
                    if (PreferedOrders.threeHeadsOrders.contains(order.getProductTitle())) {
                        order.setHeads(3);
                    }
                    if (PreferedOrders.fourHeadsOrders.contains(order.getProductTitle())) {
                        order.setHeads(4);
                    }

                    orderMap.put(order.getName(), order);
                }
            }
            if (nextPagePresent) {
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
