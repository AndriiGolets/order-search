package site.golets.viber.service;

import com.viber.bot.profile.UserProfile;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Slf4j
public class RegisteredUser {

    private List<UserProfile> userProfiles;

    public void addUserProfile(UserProfile userProfile) {
        log.info("Save UserProfile : " + userProfile);
        userProfiles.add(userProfile);
    }

}
