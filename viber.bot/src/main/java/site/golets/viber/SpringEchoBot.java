package site.golets.viber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import site.golets.viber.properties.HttpParserProperties;

@SpringBootApplication
@ConfigurationPropertiesScan("site.golets.viber.properties")
public class SpringEchoBot {

    public static void main(String[] args) {
        SpringApplication.run(SpringEchoBot.class, args);
    }

}
