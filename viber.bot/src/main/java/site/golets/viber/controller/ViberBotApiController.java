package site.golets.viber.controller;

import com.viber.bot.api.ViberBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import site.golets.viber.model.Order;
import site.golets.viber.service.RegisteredUser;

import java.util.List;

@RestController
@RequestMapping("/selenium-bot")
@Slf4j
public class ViberBotApiController {

    private final ViberBot viberBot;
    private final RegisteredUser registeredUser;

    public ViberBotApiController(ViberBot viberBot, RegisteredUser registeredUser) {
        this.viberBot = viberBot;
        this.registeredUser = registeredUser;
    }

    @PostMapping(path = "new-orders")
    public void newOrders(@RequestBody List<Order> ordersList){
        String orders = ordersList.stream().map(Order::toString).reduce((o, o1) -> o + "\n" + o1).orElse("No Orders");
        log.info(orders);

    }

}
