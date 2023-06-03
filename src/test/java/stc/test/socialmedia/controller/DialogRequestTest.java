package stc.test.socialmedia.controller;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import stc.test.socialmedia.messaging.to.DialogRequestTo;
import stc.test.socialmedia.testdata.DialogRequestTestData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static stc.test.socialmedia.messaging.controller.DialogRequestRestController.REST_URL;
import static stc.test.socialmedia.testdata.UserTestData.*;

public class DialogRequestTest extends AbstractControllerTest{

    @Test
    void sendRequest() throws Exception {
        performJwt(MockMvcRequestBuilders.post(REST_URL+"/send?userId=2"),third)
                .andExpect(status().isOk())
                .andDo(print());

        DialogRequestTo actual = new DialogRequestTo(dialogRequestService.get(2));
        DialogRequestTo expected = new DialogRequestTo(DialogRequestTestData.request1);
        assertEquals(expected,actual);
    }

    @Test
    void sendRequestWhenNotFriends() throws Exception {
        performJwt(MockMvcRequestBuilders.post(REST_URL+"/send?userId=3"),user)
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
    void acceptRequest() throws Exception{

        dialogRequestService.sendRequest(THIRD_ID,ADMIN_ID);
        performJwt(MockMvcRequestBuilders.post(REST_URL+"/1/accept"),admin)
                .andExpect(status().isOk())
                .andDo(print());
        DialogRequestTo actual = new DialogRequestTo(dialogRequestService.get(1));
        DialogRequestTo expected = new DialogRequestTo(DialogRequestTestData.accepted);
        assertEquals(expected,actual);
    }

    @Test
    void acceptNotExisting() throws Exception{
        performJwt(MockMvcRequestBuilders.post(REST_URL+"/1/accept"),admin)
                .andExpect(status().isNotFound())
                .andDo(print());
    }

}
