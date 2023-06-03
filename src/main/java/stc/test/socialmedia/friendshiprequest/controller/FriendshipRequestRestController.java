package stc.test.socialmedia.friendshiprequest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import stc.test.socialmedia.friendshiprequest.service.FriendshipRequestService;
import stc.test.socialmedia.auth.model.JwtUser;
import stc.test.socialmedia.friendshiprequest.to.FriendshipRequestTo;

import java.util.List;

import static stc.test.socialmedia.friendshiprequest.controller.FriendshipRequestRestController.REST_URL;

@RestController
@RequestMapping(value = REST_URL)
@RequiredArgsConstructor
public class FriendshipRequestRestController {

    public static final String REST_URL = "/api/friends/request";
    private final FriendshipRequestService friendshipRequestService;

    @PostMapping("/send")
    public FriendshipRequestTo sendRequest(JwtUser jwtUser,
                                         @RequestParam long userId) {
        return friendshipRequestService.sendRequest(jwtUser.id(), userId);
    }

    @PostMapping("/{requestId}/accept")
    public void acceptRequest(JwtUser jwtUser,
                              @PathVariable long requestId) {
        friendshipRequestService.acceptRequest(requestId, jwtUser.id());
    }

    @PostMapping("/{requestId}/decline")
    public void declineRequest(JwtUser jwtUser,
                               @PathVariable long requestId) {
        friendshipRequestService.declineRequest(requestId, jwtUser.id());
    }

    @DeleteMapping("/{requestId}/withdraw")
    public void withdrawRequest(JwtUser jwtUser,
                                @PathVariable long requestId) {
        friendshipRequestService.withdrawRequest(requestId, jwtUser.id());
    }

    @GetMapping("/my")
    public List<FriendshipRequestTo> getAllRequestsFromAuthorizedUser(JwtUser user) {
        return friendshipRequestService.getAllUnacceptedRequestsForSender(user.getId());
    }

    @GetMapping("/income")
    public List<FriendshipRequestTo> getAllRequestsForAuthorizedUser(JwtUser user) {
        return friendshipRequestService.getAllUnacceptedRequestForRecipient(user.getId());
    }

}
