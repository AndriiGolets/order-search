package site.golets.selenium.bot.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.golets.selenium.bot.service.SchedulerService;

import javax.servlet.http.HttpServletResponse;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrdersController {

    private final SchedulerService schedulerService;

    @Autowired
    public OrdersController(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @GetMapping(path = "start")
    public void startOrdersSearch(@RequestParam(required = false) Integer executionTime, HttpServletResponse servletResponse) {
        log.info("start request received");
        if (!schedulerService.startOrderSearchTask(executionTime)) {
            servletResponse.setStatus(201);
            log.info("Task not Finished");
        }
    }

    @GetMapping(path = "stop")
    public void stopOrdersSearch() {
        log.info("stop request received");
        schedulerService.stopTask();
    }

    @GetMapping(path = "status")
    public String getStatus() {
        log.info("status request received");
        if (schedulerService.getTaskEnabled().get()) {
            return "Finish time: " + schedulerService.getEndTask()
                    .atZone(ZoneId.of("Europe/Kiev")).format(DateTimeFormatter.ofPattern("hh:mm"));
        } else {
            return "Task disabled";
        }
    }
}
