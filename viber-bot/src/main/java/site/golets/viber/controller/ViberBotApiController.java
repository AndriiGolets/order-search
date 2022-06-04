package site.golets.viber.controller;

import com.viber.bot.api.ViberBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import site.golets.viber.model.Order;
import site.golets.viber.service.RegisteredUser;
import site.golets.viber.service.ViberBotService;

import java.util.List;

@RestController
@RequestMapping("/selenium-bot")
@Slf4j
public class ViberBotApiController {

    private final ViberBotService viberBotService;

    public ViberBotApiController(ViberBotService viberBotService) {
        this.viberBotService = viberBotService;
    }

    @PostMapping(path = "new-orders")
    public void newOrders(@RequestBody List<Order> ordersList){
        String orders = ordersList.stream().map(Order::toString).reduce((o, o1) -> o + "\n" + o1).orElse("No Orders");
        log.info(orders);
        viberBotService.sendMessageToAlUsers(orders);
    }

    @GetMapping
    public String getTest(){
        return "Test Ok";
    }

}
