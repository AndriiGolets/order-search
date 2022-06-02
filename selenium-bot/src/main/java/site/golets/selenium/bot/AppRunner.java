package site.golets.selenium.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import site.golets.selenium.bot.properties.CallbackProperties;
import site.golets.selenium.bot.properties.HttpParserProperties;

@SpringBootApplication
@EnableConfigurationProperties({HttpParserProperties.class, CallbackProperties.class})
public class AppRunner {

    public static void main(String[] args) {
        SpringApplication.run(AppRunner.class, args);
    }

}
