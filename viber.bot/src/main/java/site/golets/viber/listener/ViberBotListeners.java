package site.golets.viber.listener;

import com.google.common.util.concurrent.Futures;
import com.viber.bot.api.ViberBot;
import com.viber.bot.message.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import site.golets.viber.properties.ViberBotProperties;
import site.golets.viber.service.RegisteredUser;

import java.util.Optional;

@Slf4j
@Component
public class ViberBotListeners implements ApplicationListener<ApplicationReadyEvent> {

    private final ViberBot bot;
    private final RegisteredUser registeredUser;

    private String myViberAccountId = "81i3PwFLpDMaSH/X8JzDoQ==";

    private final ViberBotProperties viberBotProperties;

    public ViberBotListeners(ViberBot bot, RegisteredUser registeredUser, ViberBotProperties viberBotProperties) {
        this.bot = bot;
        this.registeredUser = registeredUser;
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

        });


        bot.onConversationStarted(event -> {
            registeredUser.addUserProfile(event.getUser());
            return Futures.immediateFuture(Optional.of( // send 'Hi UserName' when conversation is started
                    new TextMessage("Hi " + event.getUser().getName())));
        });
    }
}
