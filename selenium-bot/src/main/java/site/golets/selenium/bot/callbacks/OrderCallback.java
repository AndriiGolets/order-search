package site.golets.selenium.bot.callbacks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import site.golets.selenium.bot.model.Order;
import site.golets.selenium.bot.properties.CallbackProperties;

import java.util.List;

@Component
@Slf4j
public class OrderCallback {

    private final CallbackProperties properties;

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public OrderCallback(CallbackProperties properties) {
        this.properties = properties;
    }

    public void sendNewOrders(List<Order> ordersList) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<Order>> ordersEntity = new HttpEntity<>(ordersList, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(properties.getUri()+ "/new-orders", ordersEntity, String.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                log.warn("ordersFound callback rejected with code : " + response.getStatusCode());
            }
        } catch (Exception e) {
            log.error(" Problems with Connection to viberBot ", e);
        }

    }

}
