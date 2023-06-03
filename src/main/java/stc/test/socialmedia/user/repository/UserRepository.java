package stc.test.socialmedia.user.repository;

import org.springframework.transaction.annotation.Transactional;
import stc.test.socialmedia.base.repository.BaseRepository;
import stc.test.socialmedia.user.model.User;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User> {
    Optional<User> getByEmail(String email);
}