package stc.test.socialmedia.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import stc.test.socialmedia.user.model.User;
import stc.test.socialmedia.user.to.RequestUserTo;
import stc.test.socialmedia.util.UserUtil;

import static stc.test.socialmedia.user.controller.RegistrationController.REST_URL;
import static stc.test.socialmedia.util.ValidationUtil.checkNew;

@Slf4j
@RestController
@RequestMapping(REST_URL)
public class RegistrationController extends AbstractUserController {

    public static final String REST_URL = "/api/register";

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "register user")
    public User register(@Valid @RequestBody RequestUserTo requestUserTo) {
        log.info("register {}", requestUserTo);
        checkNew(requestUserTo);
        return prepareAndSave(UserUtil.createNewFromTo(requestUserTo));
    }
}
