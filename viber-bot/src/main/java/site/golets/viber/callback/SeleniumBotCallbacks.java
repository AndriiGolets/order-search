package site.golets.viber.callback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import site.golets.viber.properties.ViberBotProperties;



@Component
@Slf4j
public class SeleniumBotCallbacks {

    private ViberBotProperties properties;

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public SeleniumBotCallbacks(ViberBotProperties properties) {
        this.properties = properties;
    }

    public void getStatus() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(properties.getSeleniumBotUrl() + "/new-orders", String.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                log.warn("Get Status callback rejected with code : " + response.getStatusCode());
            }
        } catch (Exception e) {
            log.error(" Problems with Connection to seleniumBot ", e);
        }

    }

}
