package stc.test.socialmedia.controller;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import stc.test.socialmedia.friendshiprequest.to.FriendshipRequestTo;
import stc.test.socialmedia.user.to.ResponseUserTo;
import stc.test.socialmedia.util.UserUtil;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static stc.test.socialmedia.testdata.FriendshipRequestTestData.*;
import static stc.test.socialmedia.testdata.UserTestData.*;

import static stc.test.socialmedia.friendshiprequest.controller.FriendshipRequestRestController.REST_URL;

class FriendshipRequestControllerTest extends AbstractControllerTest {

    @Test
    void sendRequest() throws Exception {
        performJwt(MockMvcRequestBuilders.post(REST_URL+"/send?userId=3"),user)
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void sendRequestWhenFriends() throws Exception {
        performJwt(MockMvcRequestBuilders.post(REST_URL+"/send?userId=3"),admin)
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void sendSecondRequest() throws Exception {
        performJwt(MockMvcRequestBuilders.post(REST_URL+"/send?userId=1"),admin)
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void sendCounterRequest() throws Exception {
        performJwt(MockMvcRequestBuilders.post(REST_URL+"/send?userId=2"),user)
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void acceptRequest() throws Exception{
        performJwt(MockMvcRequestBuilders.post(REST_URL+"/1/accept"),user)
                .andExpect(status().isOk())
                .andDo(print());
        List<ResponseUserTo> actual = UserUtil.getResponseTos(userService.getFriendsOfUser(ADMIN_ID));
        List<ResponseUserTo> expected = UserUtil.getResponseTos(List.of(third,user));
        assertEquals(expected,actual);
    }

    @Test
    void acceptNotOwn() throws Exception{
        performJwt(MockMvcRequestBuilders.post(REST_URL+"/1/accept"),admin)
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void declineRequest() throws Exception{
        performJwt(MockMvcRequestBuilders.post(REST_URL+"/1/decline"),user)
                .andExpect(status().isOk())
                .andDo(print());

        assertEquals(declined,new FriendshipRequestTo(friendshipRequestService.get(1)));
    }

    @Test
    void declineNotOwn() throws Exception{
        performJwt(MockMvcRequestBuilders.post(REST_URL+"/1/decline"),third)
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void withdrawRequest() throws Exception{
        performJwt(MockMvcRequestBuilders.delete(REST_URL+"/1/withdraw"),admin)
                .andExpect(status().isOk())
                .andDo(print());

        assertThrows(NoSuchElementException.class,()->friendshipRequestService.withdrawRequest(1,ADMIN_ID));
    }

    @Test
    void getAllRequestsFromAuthorizedUser() throws Exception{
        performJwt(MockMvcRequestBuilders.get(REST_URL+"/my"),admin)
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(REQUEST_MATCHER.contentJson(List.of(request1)));
    }

    @Test
    void getAllRequestsForAuthorizedUser() throws Exception {
        performJwt(MockMvcRequestBuilders.get(REST_URL+"/income"),user)
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(REQUEST_MATCHER.contentJson(List.of(request1)));
    }
}