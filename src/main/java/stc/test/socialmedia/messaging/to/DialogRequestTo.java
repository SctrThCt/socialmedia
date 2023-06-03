package stc.test.socialmedia.messaging.to;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;
import stc.test.socialmedia.base.to.BaseTo;
import stc.test.socialmedia.messaging.model.DialogRequest;
import stc.test.socialmedia.messaging.model.DialogRequestStatus;

@Getter
@Setter
@EqualsAndHashCode
@ToString(callSuper = true)
public class DialogRequestTo extends BaseTo {
    private Long senderId;
    private Long recipientId;
    private DialogRequestStatus status;

    public DialogRequestTo (DialogRequest request) {
        super(request.id());
        this.recipientId = request.getRecipient().id();
        this.senderId = request.getSender().id();
        this.status = request.getRequestStatus();
    }
}
