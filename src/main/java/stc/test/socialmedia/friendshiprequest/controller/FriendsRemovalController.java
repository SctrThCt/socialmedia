package stc.test.socialmedia.friendshiprequest.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import stc.test.socialmedia.auth.model.JwtUser;
import stc.test.socialmedia.friendshiprequest.service.FriendshipRequestService;
import stc.test.socialmedia.user.repository.UserRepository;
import stc.test.socialmedia.user.service.UserService;
import stc.test.socialmedia.user.to.ResponseUserTo;
import stc.test.socialmedia.util.UserUtil;

import java.util.List;
import java.util.stream.Collectors;

import static stc.test.socialmedia.friendshiprequest.controller.FriendsRemovalController.REST_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = REST_URL)
public class FriendsRemovalController {

    public static final String REST_URL = "/api/friends";

    private final UserService userService;

    @Operation(summary = "Get list of friends of authorized user")
    @GetMapping
    @Transactional
    public List<ResponseUserTo> getUserFriends(JwtUser user, @PathVariable(required = false) Long userId) {

        long id = userId!=null?userId:user.getId();
        return UserUtil.getResponseTos(userService.getFriendsOfUser(id));
    }
    @Operation(summary = "Remove user with provided id from friendlst of authorized user")
    @DeleteMapping
    @Transactional
    public void removeFromFriends(JwtUser user, @RequestParam long friendId) {
        userService.removeFromFriends(user.getId(), friendId);
    }
}
