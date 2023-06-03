package stc.test.socialmedia.controller;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static stc.test.socialmedia.friendshiprequest.controller.FriendsRemovalController.REST_URL;
import static stc.test.socialmedia.testdata.UserTestData.*;

public class FriendshipRemovalControllerTest extends AbstractControllerTest{

    @Test
    void removeFromFriends() throws Exception {
        performJwt(MockMvcRequestBuilders.delete(REST_URL+"?friendId=3"),admin)
                .andExpect(status().isOk())
                .andDo(print());
        assertTrue(userService.getFriendsOfUser(ADMIN_ID).isEmpty());
    }

    @Test
    void removeFromFriendsNotFriend() throws Exception {
        performJwt(MockMvcRequestBuilders.delete(REST_URL+"?friendId=1"),admin)
                .andExpect(status().isOk())
                .andDo(print());
    }
}
