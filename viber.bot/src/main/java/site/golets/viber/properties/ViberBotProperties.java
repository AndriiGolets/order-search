package site.golets.viber.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "viberbot")
public class ViberBotProperties {

    private String authToken;
    private String webhookUrl;
    private String name;
    private String avatar;
    private String seleniumBotUrl;

}
