package site.golets.viber.listener;

import com.google.common.util.concurrent.Futures;
import com.viber.bot.api.ViberBot;
import com.viber.bot.message.Message;
import com.viber.bot.message.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import site.golets.viber.clients.SeleniumBotClient;
import site.golets.viber.properties.ViberBotProperties;
import site.golets.viber.service.RegisteredUser;

import java.util.Optional;

@Slf4j
@Component
public class ViberBotListeners implements ApplicationListener<ApplicationReadyEvent> {

    private final ViberBot bot;
    private final RegisteredUser registeredUser;
    private final SeleniumBotClient seleniumBotClient;

    private String myViberAccountId = "81i3PwFLpDMaSH/X8JzDoQ==";

    private final ViberBotProperties viberBotProperties;

    public ViberBotListeners(ViberBot bot, RegisteredUser registeredUser, SeleniumBotClient seleniumBotClient, ViberBotProperties viberBotProperties) {
        this.bot = bot;
        this.registeredUser = registeredUser;
        this.seleniumBotClient = seleniumBotClient;
        this.viberBotProperties = viberBotProperties;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent appEvent) {
        try {
            bot.setWebhook(viberBotProperties.getWebhookUrl()).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // bot.onMessageReceived((event, message, response) -> response.send(message)); // echos everything back

        // Save UserProfile From last received message
        bot.onMessageReceived((event, message, response) -> {
            registeredUser.addUserProfile(event.getSender());

            if(message instanceof TextMessage textMessage) {
                String input = textMessage.getText() == null ?  "" : textMessage.getText().toLowerCase();
                log.info("Message from viber User {} received: {}", event.getSender().getName(), input);
                if (input.startsWith("start")) {
                    int time = 0;
                    try {
                        time = Integer.parseInt(input.split(" ")[1]);
                        response.send(seleniumBotClient.start(time));
                        log.info("Start Message received. time = " + time);
                    } catch (Exception e) {
                        response.send("Wrong format. Please use: 'start ${time}' ");
                    }
                } else if (input.startsWith("stop")) {
                    response.send(seleniumBotClient.stop());
                } else if (input.startsWith("status")) {
                    response.send(seleniumBotClient.getStatus());
                } else {
                    response.send("Supported commands:\nstart ${time}\nstop\nstatus");
                }
            }
        });

        bot.onConversationStarted(event -> {
            registeredUser.addUserProfile(event.getUser());
            return Futures.immediateFuture(Optional.of( // send 'Hi UserName' when conversation is started
                    new TextMessage("Hi " + event.getUser().getName())));
        });
    }
}
