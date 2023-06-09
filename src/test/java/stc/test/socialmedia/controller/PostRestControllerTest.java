package stc.test.socialmedia.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import stc.test.socialmedia.post.controller.PostRestController;
import stc.test.socialmedia.post.model.Post;
import stc.test.socialmedia.util.JsonUtil;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static stc.test.socialmedia.post.controller.PostRestController.FEED_URL;
import static stc.test.socialmedia.post.controller.PostRestController.PROFILE_URL;
import static stc.test.socialmedia.testdata.PostTestData.*;
import static stc.test.socialmedia.testdata.UserTestData.admin;
import static stc.test.socialmedia.testdata.UserTestData.user;

class PostRestControllerTest extends AbstractControllerTest {


    @Test
    void create() throws Exception {
        performJwt(MockMvcRequestBuilders.post(PostRestController.REST_URL + "?header=ss&description=s&file=")
                .contentType(new MediaType("multipart", "form-data")), user)
                .andExpect(status().isOk())
                .andExpect(POST_MATCHER.contentJson(getNew()));
    }

    @Test
    void createInvalid() throws Exception {
        performJwt(MockMvcRequestBuilders.post(PostRestController.REST_URL + "?header=s&description=s&file=")
                .contentType(new MediaType("multipart", "form-data")), user)
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void createWithNotSpecifiedContentType() throws Exception {
        performJwt(MockMvcRequestBuilders.post(PostRestController.REST_URL + "?header=sss&description=s&file="), user)
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    void updateWithNotSpecifiedContentType() throws Exception {
        performJwt(MockMvcRequestBuilders.put(PostRestController.REST_URL + "/1?header=sss&description=s&file="), admin)
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    void update() throws Exception {
        performJwt(MockMvcRequestBuilders.put(PostRestController.REST_URL + "/1?header=sss")
                .contentType(new MediaType("multipart", "form-data")), admin)
                .andExpect(status().isOk())
                .andExpect(POST_MATCHER.contentJson(getUpdated()));
    }

    @Test
    void delete() throws Exception {
        performJwt(MockMvcRequestBuilders.delete(PostRestController.REST_URL + "/1"), admin)
                .andExpect(status().isOk());
    }

    @Test
    void deleteNotOwn() throws Exception {
        performJwt(MockMvcRequestBuilders.delete(PostRestController.REST_URL + "/1"), user)
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getFeed() throws Exception {
        MvcResult result = performJwt(MockMvcRequestBuilders.get(FEED_URL, "?direction=HIT"), user).andReturn();
        String content = result.getResponse().getContentAsString();
        List<Post> actual = JsonUtil.extractFromPage(content, Post.class);
        assertEquals(List.of(post2, post1), actual);
    }

    @Test
    void getAllUsersPost() throws Exception {
        MvcResult result = performJwt(MockMvcRequestBuilders.get(PROFILE_URL + "/1"), admin).andReturn();
        String content = result.getResponse().getContentAsString();
        List<Post> actual = JsonUtil.extractFromPage(content, Post.class);
        assertEquals(List.of(post3), actual);
    }

    @Test
    void getAllNotExistingUserPost() throws Exception {
        MvcResult result = performJwt(MockMvcRequestBuilders.get(PROFILE_URL + "/1000"), admin).andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        List<Post> actual = JsonUtil.extractFromPage(content, Post.class);
        assertEquals(Collections.emptyList(), actual);
    }


}