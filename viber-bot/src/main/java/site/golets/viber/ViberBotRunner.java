package site.golets.viber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;


@SpringBootApplication
@ConfigurationPropertiesScan("site.golets.viber.properties")
public class ViberBotRunner {

    public static void main(String[] args) {
        SpringApplication.run(ViberBotRunner.class, args);
    }

}
