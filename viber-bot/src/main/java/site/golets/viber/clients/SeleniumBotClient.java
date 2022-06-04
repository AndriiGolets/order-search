package site.golets.viber.clients;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import site.golets.viber.properties.ViberBotProperties;


@Component
@Slf4j
public class SeleniumBotClient {

    private final ViberBotProperties properties;

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public SeleniumBotClient(ViberBotProperties properties) {
        this.properties = properties;
    }

    public String getStatus() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);

        try {
            ResponseEntity<String> response = restTemplate.exchange(properties.getSeleniumBotUrl() + "/orders/status",
                    HttpMethod.GET,
                    new HttpEntity<String>(headers),
                    String.class);

            HttpStatus code = response.getStatusCode();
            if (code != HttpStatus.OK) {
                log.warn("Get Status callback rejected with code : " + code);
                return "Selenium Bot error code " + code;
            }
            return response.getBody();

        } catch (Exception e) {
            log.error(" Problems with Connection to seleniumBot ", e);
            return "Selenium Bot error " + e.getMessage();
        }

    }

    public String start(Integer executionTime) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    properties.getSeleniumBotUrl() + "/orders/start?executionTime=" + executionTime,
                    HttpMethod.GET,
                    new HttpEntity<String>(headers),
                    String.class);

            HttpStatus code = response.getStatusCode();
            if (code != HttpStatus.OK) {
                log.warn("Start callback rejected with code : " + code);
                return "Selenium Bot error code " + code;
            }
            return "Started";

        } catch (Exception e) {
            log.error(" Problems with Connection to seleniumBot ", e);
            return "Selenium Bot error " + e.getMessage();
        }

    }

    public String stop() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);

        try {
            ResponseEntity<String> response = restTemplate.exchange(properties.getSeleniumBotUrl() + "/orders/stop",
                    HttpMethod.GET,
                    new HttpEntity<String>(headers),
                    String.class);

            HttpStatus code = response.getStatusCode();
            if (code != HttpStatus.OK) {
                log.warn("Get Status callback rejected with code : " + code);
                return "Selenium Bot error code " + code;
            }
            return "Stopped";

        } catch (Exception e) {
            log.error(" Problems with Connection to seleniumBot ", e);
            return "Selenium Bot error " + e.getMessage();
        }

    }

}
