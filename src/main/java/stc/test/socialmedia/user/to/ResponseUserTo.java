package stc.test.socialmedia.user.to;

import lombok.*;
import stc.test.socialmedia.base.to.NamedTo;
import stc.test.socialmedia.user.model.User;

import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@EqualsAndHashCode
@ToString(callSuper = true)
public class ResponseUserTo extends NamedTo {

    public ResponseUserTo(User user)
    {
        super(user.getId(), user.getName());
    }
}
