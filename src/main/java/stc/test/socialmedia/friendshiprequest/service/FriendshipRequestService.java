package stc.test.socialmedia.friendshiprequest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stc.test.socialmedia.error.IllegalRequestDataException;
import stc.test.socialmedia.friendshiprequest.model.FriendshipRequest;
import stc.test.socialmedia.friendshiprequest.model.RequestStatus;
import stc.test.socialmedia.friendshiprequest.repository.FriendshipRequestRepository;
import stc.test.socialmedia.friendshiprequest.to.FriendshipRequestTo;
import stc.test.socialmedia.user.model.User;
import stc.test.socialmedia.user.repository.UserRepository;
import stc.test.socialmedia.util.ValidationUtil;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendshipRequestService {

    private final FriendshipRequestRepository friendshipRequestRepository;
    private final UserRepository userRepository;

    @Transactional
    public FriendshipRequestTo sendRequest(long senderId, long recipientId) {
        User sender = userRepository.getReferenceById(senderId);
        User recipient = userRepository.getReferenceById(recipientId);
        if (sender.getFriends().contains(recipient)) {
            throw new IllegalRequestDataException("Already friends");
        }
        Optional<FriendshipRequest> optionalRequest = friendshipRequestRepository
                .getFriendshipRequestByRecipientIdAndSenderId(senderId,recipientId);
        if (optionalRequest.isPresent()) {
            throw new IllegalRequestDataException("Request was already sent by recipient");
        }

        FriendshipRequest request = new FriendshipRequest(sender,recipient);

        sender.getSubscriptions().add(recipient);
        userRepository.save(sender);
        return new FriendshipRequestTo(friendshipRequestRepository.save(request));
    }

    @Transactional
    public void acceptRequest(long requestId, long authUserId) {
        FriendshipRequest request = get(requestId);
        User recipient = userRepository.getReferenceById(authUserId);
        User sender = request.getSender();
        ValidationUtil.assureIdConsistent(request.getRecipient(), authUserId);
        recipient.getSubscriptions().add(sender);
        recipient.getFriends().add(sender);
        sender.getFriends().add(recipient);
        friendshipRequestRepository.delete(request);
        userRepository.save(recipient);
        userRepository.save(sender);
    }

    @Transactional
    public FriendshipRequest declineRequest(long requestId, long authUserId) {
        FriendshipRequest request = get(requestId);
        ValidationUtil.assureIdConsistent(request.getRecipient(), authUserId); //проверка точно ли реквест был направлен текущему пользователю
        request.setRequestStatus(RequestStatus.DECLINED);
        return friendshipRequestRepository.save(request);
    }

    @Transactional
    public void withdrawRequest(long requestId, long authUserId) {
        FriendshipRequest request = get(requestId);
        ValidationUtil.assureIdConsistent(request.getSender(), authUserId);
        friendshipRequestRepository.delete(request);
    }

    public List<FriendshipRequestTo> getAllUnacceptedRequestForRecipient(long authUserId) {
        return friendshipRequestRepository
                .getAllFriendshipRequestsByRecipientId(authUserId, RequestStatus.SENT)
                .stream().map(r->new FriendshipRequestTo(r)).collect(Collectors.toList());
    }

    public List<FriendshipRequestTo> getAllUnacceptedRequestsForSender(long authUserId) {
        return friendshipRequestRepository.getAllFriendshipRequestsBySenderId(authUserId, RequestStatus.SENT)
                .stream().map(r->new FriendshipRequestTo(r)).collect(Collectors.toList());
    }

    public FriendshipRequest get(long id) {
        return friendshipRequestRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Not found request with id " + id));
    }

}
