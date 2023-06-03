package stc.test.socialmedia.messaging.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stc.test.socialmedia.error.IllegalRequestDataException;
import stc.test.socialmedia.friendshiprequest.model.FriendshipRequest;
import stc.test.socialmedia.messaging.model.DialogRequest;
import stc.test.socialmedia.messaging.model.DialogRequestStatus;
import stc.test.socialmedia.messaging.repository.DialogRequestRepository;
import stc.test.socialmedia.user.model.User;
import stc.test.socialmedia.user.repository.UserRepository;
import stc.test.socialmedia.util.ValidationUtil;

import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class DialogRequestService {

    private final DialogRequestRepository dialogRequestRepository;
    private final UserRepository userRepository;

    @Transactional
    public DialogRequest sendRequest(long senderId, long recipientId) {
        User sender = userRepository.getReferenceById(senderId);
        User recipient = userRepository.getReferenceById(recipientId);
        if (!sender.getFriends().contains(recipient)
                && !recipient.getFriends().contains(sender)) {
            throw new IllegalRequestDataException("Only friends can send dialog requests");
        }
        Optional<DialogRequest> optionalRequest = dialogRequestRepository
                .getDialogRequestByRecipientIdAndSenderId(senderId, recipientId);
        if (optionalRequest.isPresent()) {
            throw new IllegalRequestDataException("Request was already sent by recipient");
        }

        DialogRequest request = new DialogRequest(sender, recipient);

        sender.getSubscriptions().add(recipient);
        userRepository.save(sender);
        return dialogRequestRepository.save(request);
    }

    @Transactional
    public void acceptRequest(long requestId, long authUserId) {
        DialogRequest request = get(requestId);
        User recipient = userRepository.getReferenceById(authUserId);
        User sender = request.getSender();
        ValidationUtil.assureIdConsistent(request.getRecipient(), authUserId);
        recipient.getSubscriptions().add(sender);
        recipient.getFriends().add(sender);
        sender.getFriends().add(recipient);
        request.setRequestStatus(DialogRequestStatus.ACCEPTED);
        dialogRequestRepository.save(request);
        userRepository.save(recipient);
        userRepository.save(sender);
    }

    public DialogRequest get(long id) {
        return dialogRequestRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Not found request with id " + id));
    }
}
