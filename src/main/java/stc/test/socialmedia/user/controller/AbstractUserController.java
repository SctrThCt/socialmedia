package stc.test.socialmedia.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import stc.test.socialmedia.error.IllegalRequestDataException;
import stc.test.socialmedia.user.model.User;
import stc.test.socialmedia.user.repository.UserRepository;
import stc.test.socialmedia.util.UserUtil;
import stc.test.socialmedia.auth.model.JwtUser;

@Slf4j
public abstract class AbstractUserController {

    @Autowired
    protected UserRepository repository;

    public ResponseEntity<User> get(long id) {
        log.info("get {}", id);
        return ResponseEntity.of(repository.findById(id));
    }

    public void delete(long id) {
        log.info("delete {}", id);
        repository.deleteExisted(id);
    }

    protected User findByJwtUser(JwtUser jwtUser) {
        return repository.findById(jwtUser.id()).orElseThrow(
                () -> new IllegalRequestDataException("User id='" + jwtUser.getId() + "' was not found"));
    }

    protected User prepareAndSave(User user) {
        return repository.save(UserUtil.prepareToSave(user));
    }
}