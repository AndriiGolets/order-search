package site.golets.viber.service;

import com.viber.bot.api.MessageDestination;
import com.viber.bot.api.ViberBot;
import com.viber.bot.message.TextMessage;
import org.springframework.stereotype.Service;

@Service
public class ViberBotService {

    private final RegisteredUser registeredUser;

    private final ViberBot viberBot;

    public ViberBotService(RegisteredUser registeredUser, ViberBot viberBot) {
        this.registeredUser = registeredUser;
        this.viberBot = viberBot;
    }

    public void sendMessageToAlUsers(String message){
        registeredUser.getUserProfiles().values().forEach(u ->
                viberBot.sendMessage(new MessageDestination(u), new TextMessage(message)));
    }

}
