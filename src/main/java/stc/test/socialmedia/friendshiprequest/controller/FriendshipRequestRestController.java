package stc.test.socialmedia.friendshiprequest.controller;

import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Send request to user from authorized user")
    @PostMapping("/send")
    public FriendshipRequestTo sendRequest(JwtUser jwtUser,
                                         @RequestParam long userId) {
        return friendshipRequestService.sendRequest(jwtUser.id(), userId);
    }

    @Operation(summary = "Accept request with provided id with authorized user")
    @PostMapping("/{requestId}/accept")
    public void acceptRequest(JwtUser jwtUser,
                              @PathVariable long requestId) {
        friendshipRequestService.acceptRequest(requestId, jwtUser.id());
    }

    @Operation(summary = "Decline request with provided id with authorized user")
    @PostMapping("/{requestId}/decline")
    public void declineRequest(JwtUser jwtUser,
                               @PathVariable long requestId) {
        friendshipRequestService.declineRequest(requestId, jwtUser.id());
    }
    @Operation(summary = "Withdraw request with provided id sent by authorized user")
    @DeleteMapping("/{requestId}/withdraw")
    public void withdrawRequest(JwtUser jwtUser,
                                @PathVariable long requestId) {
        friendshipRequestService.withdrawRequest(requestId, jwtUser.id());
    }

    @Operation(summary = "Get list of requests from authorized users")
    @GetMapping("/my")
    public List<FriendshipRequestTo> getAllRequestsFromAuthorizedUser(JwtUser user) {
        return friendshipRequestService.getAllUnacceptedRequestsForSender(user.getId());
    }

    @Operation(summary = "Get list of requests to authorized user")
    @GetMapping("/income")
    public List<FriendshipRequestTo> getAllRequestsForAuthorizedUser(JwtUser user) {
        return friendshipRequestService.getAllUnacceptedRequestForRecipient(user.getId());
    }

}
