package stc.test.socialmedia.messaging.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import stc.test.socialmedia.base.model.BaseEntity;
import stc.test.socialmedia.user.model.User;

@Entity
@Getter
@Setter
@Table(name = "dialog_request", uniqueConstraints = @UniqueConstraint(columnNames = {"sender_id", "recipient_id"}, name = "uk_users_dialog_request"))
public class DialogRequest extends BaseEntity {

    @JoinColumn(name = "sender_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User sender;
    @JoinColumn(name = "recipient_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User recipient;

    @Column(name = "request_status")
    @Enumerated(EnumType.STRING)
    private DialogRequestStatus requestStatus;

    public DialogRequest(long id, User sender,User recipient) {
        super(id);
        this.sender=sender;
        this.recipient=recipient;
        this.requestStatus=DialogRequestStatus.SENT;
    }
    public DialogRequest(User sender, User recipient) {
        this.sender = sender;
        this.recipient = recipient;
        this.requestStatus=DialogRequestStatus.SENT;
    }

    public DialogRequest (long id, User sender, User recipient, DialogRequestStatus status) {
        this.id=id;
        this.sender=sender;
        this.recipient=recipient;
        this.requestStatus=status;
    }


}
