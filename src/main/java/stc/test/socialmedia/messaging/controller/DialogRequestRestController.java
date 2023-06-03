package stc.test.socialmedia.messaging.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import stc.test.socialmedia.auth.model.JwtUser;
import stc.test.socialmedia.messaging.to.DialogRequestTo;
import stc.test.socialmedia.messaging.service.DialogRequestService;

import static stc.test.socialmedia.messaging.controller.DialogRequestRestController.REST_URL;

@RestController
@RequestMapping(value = REST_URL)
@RequiredArgsConstructor
public class DialogRequestRestController {

    public static final String REST_URL = "/api/dialog/request";

    private final DialogRequestService dialogRequestService;

    @PostMapping("/send")
    public DialogRequestTo sendRequest(JwtUser jwtUser,
                                       @RequestParam long userId) {
        return new DialogRequestTo(dialogRequestService.sendRequest(jwtUser.id(), userId));
    }

    @PostMapping("/{requestId}/accept")
    public void acceptRequest(JwtUser jwtUser,
                              @PathVariable long requestId) {
        dialogRequestService.acceptRequest(requestId, jwtUser.id());
    }
}
