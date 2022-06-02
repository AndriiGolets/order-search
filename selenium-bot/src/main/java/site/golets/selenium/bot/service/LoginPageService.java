package site.golets.selenium.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;
import site.golets.selenium.bot.exceptions.LoginErrorException;
import site.golets.selenium.bot.properties.HttpParserProperties;


@Service
@Slf4j
public class LoginPageService {

    public static final int delayAfterLogin = 5000;
    private final HttpParserProperties properties;
    private final WebDriver driver;

    public LoginPageService(HttpParserProperties properties, WebDriver driver) {
        this.properties = properties;
        this.driver = driver;
    }

    public void verifyLogin() {

        try {
            if (isLoginPage()) {
                log.info("Logging in.");
                login();
            }
        } catch (Exception e) {
            e.printStackTrace();
            driver.quit();
            throw new LoginErrorException("Can`t login. Unexpected error : " + e.getMessage(), e);
        }
        if (!isOrdersPage()) {
            log.error("Can not log in. Current Page : " + driver.getTitle());
            driver.quit();
            throw new LoginErrorException("Can`t login. Wrong page name : " + driver.getTitle());
        }
        log.info("Orders page opened");
    }

    private void login()  {
        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys(properties.getUserEmail());
        WebElement pass = driver.findElement(By.id("password"));
        pass.sendKeys(properties.getPassword());
        WebElement button = driver.findElement(By.className("stk-login-button"));
        button.click();
        try {
            Thread.sleep(properties.getDelayAfterLogin());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isLoginPage(){
        return properties.getLoginPageName().equals(driver.getTitle());
    }

    public boolean isOrdersPage(){
        return properties.getOrdersListPageName().equals(driver.getTitle());
    }
}
