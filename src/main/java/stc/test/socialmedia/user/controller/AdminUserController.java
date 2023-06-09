package stc.test.socialmedia.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import stc.test.socialmedia.user.model.User;
import stc.test.socialmedia.user.service.UserService;

import java.net.URI;
import java.util.List;

import static stc.test.socialmedia.user.controller.AdminUserController.REST_URL;
import static stc.test.socialmedia.util.ValidationUtil.assureIdConsistent;
import static stc.test.socialmedia.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminUserController {

    public static final String REST_URL = "/api/admin/users";

    private final UserService userService;

    @GetMapping("/{id}")
    @Operation(summary = "Get user any by provided id")
    public User get(@PathVariable long id) {
        return userService.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete user any by provided id")
    public void delete(@PathVariable long id) {
        log.info("delete {}", id);
        userService.delete(id);
    }

    @GetMapping
    @Operation(summary = "Get list of all users")
    public List<User> getAll() {
        log.info("getAll");
        return userService.getAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "create user and return his uri")
    public ResponseEntity<User> createWithLocation(@Valid @RequestBody User user) {
        log.info("create {}", user);
        checkNew(user);
        User created = userService.prepareAndSave(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "update any user with provided id")
    public void update(@Valid @RequestBody User user, @PathVariable long id) {
        log.info("update {} with id={}", user, id);
        assureIdConsistent(user, id);
        userService.prepareAndSave(user);
    }

    @GetMapping("/by-email")
    @Operation(summary = "Get any user by email")
    public User getByEmail(@RequestParam String email) {
        log.info("getByEmail {}", email);
        return userService.getByEmail(email);
    }
}