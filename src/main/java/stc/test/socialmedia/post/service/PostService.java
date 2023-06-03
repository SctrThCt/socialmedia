package stc.test.socialmedia.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import stc.test.socialmedia.post.model.Post;
import stc.test.socialmedia.post.repository.PostRepository;
import stc.test.socialmedia.user.model.User;
import stc.test.socialmedia.user.repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static stc.test.socialmedia.util.ValidationUtil.assureIdConsistent;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    public Page<Post> getPostBySubscription(long userId, Pageable pageable) {
        User user = userRepository.getReferenceById(userId);

        Set<Long> subscriptions = user.getSubscriptions().stream().map(u -> u.getId()).collect(Collectors.toSet());
        return postRepository.findAllSubscribedPosts(subscriptions, pageable);
    }

    public Page<Post> getPostsByUserId(Pageable pageable, long userId) {
        return postRepository.findAllByUserId(userId, pageable);
    }

    @Transactional
    public Post create(Long userId, Post post, @Nullable MultipartFile file) throws IOException {

        post.setUser(userRepository.getReferenceById(userId));
        if (file != null) {
            post.setPhotoPath(processFile(file));
        }
        return postRepository.save(post);
    }

    @Transactional
    public Post update(long postId, long userId,
                       @Nullable MultipartFile file,
                       @Nullable String description,
                       @Nullable String header) throws IOException {
        Post toUpdate = postRepository.getReferenceById(postId);
        assureIdConsistent(toUpdate, postId);
        assureIdConsistent(toUpdate.getUser(),userId);
        toUpdate.setName(header != null ? header : toUpdate.getName());
        toUpdate.setDescription(description != null ? description : toUpdate.getDescription());
        toUpdate.setPhotoPath(file != null ? processFile(file) : toUpdate.getPhotoPath());
        return postRepository.save(toUpdate);
    }

    @Transactional
    public void delete(long postId, long userId) {
        Post toDelete = postRepository.getReferenceById(postId);
        assureIdConsistent(toDelete.getUser(), userId);
        postRepository.delete(toDelete);
    }

    public String processFile(MultipartFile file) throws IOException {
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();
        String uuid = UUID.randomUUID().toString();
        String resultFileName = uuid + "-" + file.getOriginalFilename();
        file.transferTo(new File(uploadPath + "/" + resultFileName));
        return resultFileName;
    }
}
