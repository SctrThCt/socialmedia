package stc.test.socialmedia.testdata;

import stc.test.socialmedia.MatcherFactory;
import stc.test.socialmedia.friendshiprequest.model.FriendshipRequest;
import stc.test.socialmedia.friendshiprequest.model.RequestStatus;
import stc.test.socialmedia.friendshiprequest.to.FriendshipRequestTo;
import stc.test.socialmedia.post.model.Post;

import static stc.test.socialmedia.testdata.UserTestData.admin;
import static stc.test.socialmedia.testdata.UserTestData.user;

public class FriendshipRequestTestData {

    public static final MatcherFactory.Matcher<FriendshipRequestTo> REQUEST_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(FriendshipRequestTo.class);
    public static final FriendshipRequestTo request1 = new FriendshipRequestTo(new FriendshipRequest(1,admin,user, RequestStatus.SENT));
    public static final FriendshipRequestTo declined = new FriendshipRequestTo(new FriendshipRequest(1,admin,user,RequestStatus.DECLINED));
}
