package stc.test.socialmedia.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import stc.test.socialmedia.post.model.Post;
import stc.test.socialmedia.post.service.PostService;
import stc.test.socialmedia.auth.model.JwtUser;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class PostRestController {
    public final static String FEED_URL = "/api/feed";
    public final static String REST_URL = "/api/post";
    public final static String PROFILE_URL = "/api/profile/{userId}";
    private final PostService postService;

    @Operation(summary = "Get posts from subscriptions of authorized user")
    @GetMapping(value = FEED_URL)
    public Page<Post> getPostsForAuthorizedUser(
            JwtUser jwtUser,
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "20") int pageSize,
            @RequestParam(required = false, defaultValue = "DESC") Sort.Direction direction) {
        Sort sort = Sort.by(direction,"created","id");
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
        return postService.getPostBySubscription(jwtUser.id(),pageable);
    }

    @Operation(summary = "Get all posts from provided user id")
    @GetMapping(value = PROFILE_URL)
    public Page<Post> getAllUsersPost(@PathVariable long userId,
                                      @RequestParam(required = false, defaultValue = "0") int pageNumber,
                                      @RequestParam(required = false, defaultValue = "20") int pageSize,
                                      @RequestParam(required = false, defaultValue = "DESC") Sort.Direction direction) {
        Sort sort = Sort.by(direction,"created","id");
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
        return postService.getPostsByUserId(pageable, userId);
    }

    @Operation(summary = "create post from authorized user")
    @PostMapping(value = REST_URL, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Post create(
            JwtUser jwtUser,
            @RequestParam(required = false) MultipartFile file,
            @RequestParam String header,
            @RequestParam(required = false, defaultValue = "") String description) throws IOException {
        Post post = new Post();
        post.setName(header);
        post.setDescription(description);
        return postService.create(jwtUser.getId(), post, file);
    }

    @Operation(summary = "update post with provided post id, if it was created by authorized user")
    @PutMapping(value = REST_URL + "/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Post update(
            JwtUser jwtUser,
            @PathVariable long postId,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String header,
            @RequestParam(required = false) MultipartFile file) throws IOException {
        return postService.update(postId, jwtUser.getId(), file, description, header);
    }

    @Operation(summary = "delete post by provided id, if was created by authorized user")
    @DeleteMapping(value = REST_URL + "/{postId}")
    public void delete(JwtUser jwtUser, @PathVariable long postId) {
        postService.delete(postId, jwtUser.id());
    }
}
