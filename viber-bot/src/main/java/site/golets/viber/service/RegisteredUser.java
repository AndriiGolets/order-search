package site.golets.viber.service;

import com.viber.bot.profile.UserProfile;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Getter
@Slf4j
public class RegisteredUser {

    private Map<String, UserProfile> userProfiles;

    public void addUserProfile(UserProfile userProfile) {
        log.info("Save UserProfile : " + userProfile);
        userProfiles.put(userProfile.getName(), userProfile);
    }

}
