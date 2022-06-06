package site.golets.selenium.bot.callbacks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.golets.selenium.bot.model.Order;

import java.util.ArrayList;
import java.util.List;

//@SpringBootTest
public class OrderCallbackTest extends Assertions {

//    @Autowired
    OrderCallback orderCallback;

//    @Test
    public void sendNewOrders(){

        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order().setName("#70311").setDate("").setProductTitle("King of the North").setArtist(""));
        orderCallback.sendNewOrders(orderList);
    }


}
