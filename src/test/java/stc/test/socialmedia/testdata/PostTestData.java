package stc.test.socialmedia.testdata;

import org.springframework.data.domain.*;
import stc.test.socialmedia.MatcherFactory;
import stc.test.socialmedia.post.model.Post;

import java.util.List;

import static stc.test.socialmedia.testdata.UserTestData.admin;
import static stc.test.socialmedia.testdata.UserTestData.user;

public class PostTestData {
    public static final MatcherFactory.Matcher<Post> POST_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Post.class, "created","user");
    public static final Post post1 = new Post(1, "umnaya misl", "na samom dele net", admin);
    public static final Post post2 = new Post(2,"ne umnaya misl", null, admin);
    public static final Post post3 = new Post(3,"a","b",user);

    public static Post getNew() {
        return new Post(4,"ss","s", user);
    }

    public static Post getUpdated()
    {
        return new Post(1,"sss", "na samom dele net",admin);
    }


}
