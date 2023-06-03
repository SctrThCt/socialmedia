package stc.test.socialmedia.friendshiprequest.to;

import lombok.Data;
import lombok.NoArgsConstructor;
import stc.test.socialmedia.base.to.BaseTo;
import stc.test.socialmedia.friendshiprequest.model.FriendshipRequest;

@Data
@NoArgsConstructor
public class FriendshipRequestTo extends BaseTo {

    private long senderId;

    public FriendshipRequestTo(FriendshipRequest request)
    {
        super(request.getId());
        this.senderId=request.getSender().getId();
    }
}
