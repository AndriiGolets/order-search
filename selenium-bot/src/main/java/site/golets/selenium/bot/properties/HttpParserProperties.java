package site.golets.selenium.bot.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "http.parser")
public class HttpParserProperties {

    private String seleniumUrl;
    private String orderPageUrl;
    private String userEmail;
    private String password;
    private String ordersListPageName;
    private String loginPageName;
    private Integer delayAfterLogin;
    private Integer delayAfterOrderPageLoad;

}
