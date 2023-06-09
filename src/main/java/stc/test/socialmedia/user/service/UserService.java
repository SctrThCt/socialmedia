package stc.test.socialmedia.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stc.test.socialmedia.auth.model.JwtUser;
import stc.test.socialmedia.user.model.User;
import stc.test.socialmedia.user.repository.UserRepository;
import stc.test.socialmedia.util.UserUtil;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public User get(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("Not found user with id " + userId));
    }

    @Transactional
    public void subscribe(long authUserId, long subscribeTo) {
        User authUser = userRepository.getReferenceById(authUserId);
        User subscription = userRepository.getReferenceById(subscribeTo);
        authUser.getSubscriptions().add(subscription);
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

    @Transactional
    public void delete(long id) {
        if (userRepository.delete(id) == 0) {
            throw new NoSuchElementException("User with id " + id + " not found");
        }
    }

    public List<User> getAll() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "name", "email"));
    }

    public User getByEmail(String email) {
        return userRepository.getByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Not found user with email: " + email));
    }

    @Transactional
    public User prepareAndSave(User user) {
        return userRepository.save(UserUtil.prepareToSave(user));
    }

    public User findByJwtUser(JwtUser jwtUser) {
        return userRepository.findById(jwtUser.id()).orElseThrow(
                () -> new NoSuchElementException("User id='" + jwtUser.getId() + "' was not found"));
    }

}
