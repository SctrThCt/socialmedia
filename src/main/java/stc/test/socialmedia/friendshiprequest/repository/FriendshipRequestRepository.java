package stc.test.socialmedia.friendshiprequest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import stc.test.socialmedia.friendshiprequest.model.FriendshipRequest;
import stc.test.socialmedia.friendshiprequest.model.RequestStatus;
import stc.test.socialmedia.base.repository.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface FriendshipRequestRepository extends BaseRepository<FriendshipRequest> {

    @Query("select r from FriendshipRequest r where r.requestStatus=:status and r.recipient.id=:recipientId")
    List<FriendshipRequest> getAllFriendshipRequestsByRecipientId(long recipientId, RequestStatus status);

    @Query("select r from FriendshipRequest r where r.requestStatus=:status and r.sender.id=:senderId")
    List<FriendshipRequest> getAllFriendshipRequestsBySenderId(long senderId, RequestStatus status);

    Optional<FriendshipRequest> getFriendshipRequestByRecipientIdAndSenderId(long recipientId, long senderId);
}
