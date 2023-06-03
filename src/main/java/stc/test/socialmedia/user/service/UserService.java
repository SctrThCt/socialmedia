package stc.test.socialmedia.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stc.test.socialmedia.user.model.User;
import stc.test.socialmedia.user.repository.UserRepository;
import stc.test.socialmedia.user.to.ResponseUserTo;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void subscribe(long authUserId, long subscribeTo) {
        User authUser = userRepository.getReferenceById(authUserId);
        User subscription = userRepository.getReferenceById(subscribeTo);
        authUser.getSubscriptions().add(subscription);
        log.warn("trying to subscribe {}, id {} to {},id {}", authUser.getName(), authUser.getId(), subscription.getName(), subscription.getId());
        userRepository.save(authUser);
    }

    public void unsubscribe(long authUserId, long subscription) {
        User authUser = userRepository.getReferenceById(authUserId);
        User subscriptionUser = userRepository.getReferenceById(subscription);
        authUser.getSubscriptions().remove(subscriptionUser);
        userRepository.save(authUser);
    }

    @Transactional
    public void removeFromFriends(long authUserId, long friendId) {
        User authUser = userRepository.getReferenceById(authUserId);
        User friend = userRepository.getReferenceById(friendId);
        authUser.getSubscriptions().remove(friend);
        authUser.getFriends().remove(friend);
        friend.getFriends().remove(authUser);
        userRepository.save(authUser);
        userRepository.save(friend);
    }

    @Transactional
    public Set<User> getFriendsOfUser(long userId) {
        return userRepository.getReferenceById(userId).getFriends();
    }

}
