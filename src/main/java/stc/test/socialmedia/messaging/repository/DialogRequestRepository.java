package stc.test.socialmedia.messaging.repository;

import stc.test.socialmedia.base.repository.BaseRepository;
import stc.test.socialmedia.friendshiprequest.model.FriendshipRequest;
import stc.test.socialmedia.messaging.model.DialogRequest;

import java.util.Optional;

public interface DialogRequestRepository extends BaseRepository<DialogRequest> {

    Optional<DialogRequest> getDialogRequestByRecipientIdAndSenderId(long recipientId, long senderId);
}
