package site.golets.selenium.bot.config;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.golets.selenium.bot.properties.HttpParserProperties;

import java.net.URL;

@Configuration
@Slf4j
public class AppConfig {

    private final HttpParserProperties properties;

    @Autowired
    public AppConfig(HttpParserProperties properties) {
        this.properties = properties;
    }

}
