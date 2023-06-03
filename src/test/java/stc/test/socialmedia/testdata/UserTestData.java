package stc.test.socialmedia.testdata;

import stc.test.socialmedia.MatcherFactory;
import stc.test.socialmedia.user.model.Role;
import stc.test.socialmedia.user.model.User;
import stc.test.socialmedia.util.JsonUtil;

import java.util.Collections;
import java.util.Date;

public class UserTestData {

    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "registered", "password","subscriptions","subscribers","friends");

    public static final long USER_ID = 1;
    public static final long ADMIN_ID = 2;
    public static final long THIRD_ID = 3;
    public static final long NOT_FOUND = 100;
    public static final String USER_MAIL = "user@yandex.ru";
    public static final String ADMIN_MAIL = "admin@gmail.com";

    public static final String THIRD_MAIL = "third@th.rd";

    public static final User user = new User(USER_ID, "User", USER_MAIL, "password", Role.USER);
    public static final User admin = new User(ADMIN_ID, "Admin", ADMIN_MAIL, "admin", Role.ADMIN, Role.USER);

    public static final User third = new User(THIRD_ID, "Third",THIRD_MAIL, "the_third", Role.USER);
    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", false, new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        return new User(USER_ID, "UpdatedName", USER_MAIL, "newPass", false, new Date(), Collections.singleton(Role.ADMIN));
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
