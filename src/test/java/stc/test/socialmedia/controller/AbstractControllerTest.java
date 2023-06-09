package stc.test.socialmedia.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.support.incrementer.MariaDBSequenceMaxValueIncrementer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import stc.test.socialmedia.friendshiprequest.service.FriendshipRequestService;
import stc.test.socialmedia.messaging.repository.DialogRequestRepository;
import stc.test.socialmedia.messaging.service.DialogRequestService;
import stc.test.socialmedia.post.controller.PostRestController;
import stc.test.socialmedia.post.model.Post;
import stc.test.socialmedia.user.model.User;
import stc.test.socialmedia.user.service.UserService;
import stc.test.socialmedia.util.JsonUtil;

import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static stc.test.socialmedia.testdata.UserTestData.admin;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public abstract class AbstractControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected UserService userService;
    @Autowired
    protected FriendshipRequestService friendshipRequestService;
    @Autowired
    protected DialogRequestService dialogRequestService;

    protected static ConcurrentHashMap<User, String> userJwtMap = new ConcurrentHashMap<>();

    protected ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder);
    }

    protected String getJWT(User user) {
        try {
            return getJWT(user.getEmail(), user.getPassword())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected ResultActions getJWT(String email, String password) throws Exception {
        return perform(post("/token").with(httpBasic(email, password)));
    }

    protected ResultActions performJwt(MockHttpServletRequestBuilder builder, User user) throws Exception {
        return perform(builder
                .header("Authorization", "Bearer " + userJwtMap.computeIfAbsent(user, this::getJWT)));
    }
}
