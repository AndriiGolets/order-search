package site.golets.viber.config;

import com.viber.bot.ViberSignatureValidator;
import com.viber.bot.api.ViberBot;
import com.viber.bot.profile.BotProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.golets.viber.properties.HttpParserProperties;
import site.golets.viber.properties.ViberBotProperties;

import java.net.MalformedURLException;
import java.net.URL;

@Configuration
public class AppConfig {


    private final ViberBotProperties viberBotProperties;
    private final HttpParserProperties properties;



    @Autowired
    public AppConfig(ViberBotProperties viberBotProperties, HttpParserProperties properties) {
        this.viberBotProperties = viberBotProperties;
        this.properties = properties;
    }

    @Bean
    ViberBot viberBot() {
        return new ViberBot(new BotProfile(viberBotProperties.getName(),
                viberBotProperties.getAvatar()), viberBotProperties.getAuthToken());
    }

    @Bean
    ViberSignatureValidator signatureValidator() {
        return new ViberSignatureValidator(viberBotProperties.getAuthToken());
    }

}
