package stc.test.socialmedia.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import stc.test.socialmedia.user.model.User;
import stc.test.socialmedia.user.repository.UserRepository;
import stc.test.socialmedia.user.to.RequestUserTo;
import stc.test.socialmedia.util.JsonUtil;
import stc.test.socialmedia.util.UserUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static stc.test.socialmedia.testdata.UserTestData.*;
import static stc.test.socialmedia.user.controller.ProfileController.REST_URL;

public class ProfileControllerTest extends AbstractControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void get() throws Exception {
        performJwt(MockMvcRequestBuilders.get(REST_URL), user)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(user));
    }

    @Test
    void getUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void update() throws Exception {
        RequestUserTo updatedTo = new RequestUserTo(null, "newName", USER_MAIL, "newPassword");
        performJwt(MockMvcRequestBuilders.put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)), user)
                .andDo(print())
                .andExpect(status().isNoContent());

        USER_MATCHER.assertMatch(userRepository.getReferenceById(USER_ID), UserUtil.updateFromTo(new User(user), updatedTo));
    }

    @Test
    void updateInvalid() throws Exception {
        RequestUserTo updatedTo = new RequestUserTo(null, null, "password", null);
        performJwt(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)), user)
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}
