package stc.test.socialmedia.friendshiprequest.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import stc.test.socialmedia.base.model.BaseEntity;
import stc.test.socialmedia.user.model.User;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "friendship_request", uniqueConstraints = @UniqueConstraint(columnNames = {"sender_id", "recipient_id"}, name = "uk_users_friendship_request"))
public class FriendshipRequest extends BaseEntity {

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
    private RequestStatus requestStatus;

    public FriendshipRequest(User sender, User recipient) {
        this.sender = sender;
        this.recipient = recipient;
        this.requestStatus=RequestStatus.SENT;
    }

    public FriendshipRequest(long id, User sender, User recipient, RequestStatus status) {
        super(id);
        this.sender = sender;
        this.recipient = recipient;
        this.requestStatus = status;
    }
}
