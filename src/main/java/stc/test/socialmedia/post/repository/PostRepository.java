package stc.test.socialmedia.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import stc.test.socialmedia.post.model.Post;
import stc.test.socialmedia.base.repository.BaseRepository;

import java.util.Set;

public interface PostRepository extends BaseRepository<Post> {

    @Query("select p from Post p where p.user.id in:userId")
    Page<Post> findAllSubscribedPosts(Set<Long> userId, Pageable pageable);

    Page<Post> findAllByUserId(long userId, Pageable pageable);
}
