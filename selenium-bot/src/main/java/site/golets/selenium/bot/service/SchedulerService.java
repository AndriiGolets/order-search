package site.golets.selenium.bot.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.golets.selenium.bot.callbacks.OrderCallback;
import site.golets.selenium.bot.model.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class SchedulerService {

    @Getter
    private final AtomicBoolean taskEnabled = new AtomicBoolean(false);

    @Getter
    public LocalDateTime endTask = LocalDateTime.now();

    private final OrderCallback orderCallback;

    private final OrdersPageService ordersPageService;

    private final LoginPageService loginPageService;

    @Autowired
    public SchedulerService(OrderCallback orderCallback, OrdersPageService ordersPageService, LoginPageService loginPageService) {
        this.orderCallback = orderCallback;
        this.ordersPageService = ordersPageService;
        this.loginPageService = loginPageService;
    }

    public void pageReload() {
        if (taskNotFinished()) {
            ordersPageService.visitOrdersPage();
            loginPageService.verifyLogin();
            ordersPageService.parsePageForOrders();
        }
    }

    public boolean startOrderSearchTask(Integer executionTime) {

        if (executionTime == null) {
            executionTime = 24;
        }

        if (!taskEnabled.get()) {
            endTask = LocalDateTime.now().plusHours(executionTime);
            taskEnabled.set(true);
            return true;
        } else {
            return false;
        }
    }

    public void scanNewOrders (){
        List<Order> orderList = new ArrayList<>();
        while (true) {
            Order order = ordersPageService.getNewOrders().poll();
            if (order != null ) {
                orderList.add(order);
            } else {
                break;
            }
        }

        if(!orderList.isEmpty()) {
            orderCallback.sendNewOrders(orderList);
        }
    }

    public void stopTask(){
        taskEnabled.set(false);
        ordersPageService.getOrdersMap().clear();
    }

    private boolean taskNotFinished() {
        if (endTask.isBefore(LocalDateTime.now())){
            taskEnabled.set(false);
        }
        return taskEnabled.get();
    }


}
