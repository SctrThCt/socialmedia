package stc.test.socialmedia.auth.model;

import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.NonNull;
import stc.test.socialmedia.user.model.User;

@Getter
@ToString(of = "user")
public class AuthUser extends org.springframework.security.core.userdetails.User {

    private final User user;

    public AuthUser(@NonNull User user) {
        super(user.getEmail(), user.getPassword(), user.getRoles());
        this.user = user;
    }

    public long id() {
        return user.id();
    }
}