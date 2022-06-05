package site.golets.selenium.bot.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import site.golets.selenium.bot.service.SchedulerService;

@Configuration
@EnableScheduling
@Slf4j
public class TaskScheduler implements SchedulingConfigurer {

    private final SchedulerService schedulerService;

    @Autowired
    public TaskScheduler(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @Scheduled(fixedRate = 10000)
    public void pageReloadTask() {
        log.debug("pageReloadTask started");
        schedulerService.pageReload();
    }

    @Scheduled(fixedRate = 5000)
    public void newOrdersScanTask() throws InterruptedException {
        log.debug("newOrdersScanTask started");
        schedulerService.scanNewOrders();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

    }
}
