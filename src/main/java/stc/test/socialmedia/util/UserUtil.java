package stc.test.socialmedia.util;

import lombok.experimental.UtilityClass;
import stc.test.socialmedia.user.model.Role;
import stc.test.socialmedia.user.model.User;
import stc.test.socialmedia.user.to.RequestUserTo;
import stc.test.socialmedia.user.to.ResponseUserTo;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static stc.test.socialmedia.config.LoginSecurityConfiguration.PASSWORD_ENCODER;

@UtilityClass
public class UserUtil {

    public static User createNewFromTo(RequestUserTo requestUserTo) {
        return new User(null, requestUserTo.getName(), requestUserTo.getEmail().toLowerCase(), requestUserTo.getPassword(), Role.USER);
    }

    public static User updateFromTo(User user, RequestUserTo requestUserTo) {
        user.setName(requestUserTo.getName());
        user.setEmail(requestUserTo.getEmail().toLowerCase());
        user.setPassword(requestUserTo.getPassword());
        return user;
    }

    public static User prepareToSave(User user) {
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }

    public static List<ResponseUserTo> getResponseTos(Collection<User> users) {
       return users.stream().map(u->new ResponseUserTo(u))
                .collect(Collectors.toList());
    }
}