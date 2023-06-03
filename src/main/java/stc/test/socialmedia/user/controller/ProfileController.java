package stc.test.socialmedia.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import stc.test.socialmedia.user.model.User;
import stc.test.socialmedia.user.service.UserService;
import stc.test.socialmedia.user.to.RequestUserTo;
import stc.test.socialmedia.user.to.ResponseUserTo;
import stc.test.socialmedia.util.UserUtil;
import stc.test.socialmedia.auth.model.JwtUser;

import java.util.Set;
import java.util.stream.Collectors;

import static stc.test.socialmedia.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = ProfileController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfileController extends AbstractUserController {
    public static final String REST_URL = "/api/profile";

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get authorized user")
    public User get(JwtUser jwtUser) {
        return findByJwtUser(jwtUser);
    }

    @GetMapping("/subscriptions")
    @Operation(summary = "Get subscription of authorized user")
    public Set<ResponseUserTo> getSubsriptions(JwtUser jwtUser) {
        return findByJwtUser(jwtUser).getSubscriptions().stream().map(u -> new ResponseUserTo(u)).collect(Collectors.toSet());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete authorized user")
    public void delete(JwtUser jwtUser) {
        super.delete(jwtUser.id());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @Operation(summary = "Update authorized user")
    public void update(@RequestBody @Valid RequestUserTo requestUserTo, JwtUser jwtUser) {
        assureIdConsistent(requestUserTo, jwtUser.id());
        User user = findByJwtUser(jwtUser);
        prepareAndSave(UserUtil.updateFromTo(user, requestUserTo));
    }

    @Transactional
    @PostMapping("/{userId}/subscribe")
    public void subscribe(JwtUser jwtUser, @PathVariable int userId) {
        userService.subscribe(jwtUser.id(), userId);
    }

    @Transactional
    @PostMapping("/{userId}/unsubscribe")
    public void unsubscribe(JwtUser jwtUser, @PathVariable int userId) {
        userService.unsubscribe(jwtUser.id(), userId);
    }
}