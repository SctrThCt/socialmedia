package stc.test.socialmedia.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.jdbc.support.incrementer.MariaDBSequenceMaxValueIncrementer;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import stc.test.socialmedia.post.controller.PostRestController;
import stc.test.socialmedia.post.model.Post;
import stc.test.socialmedia.util.JsonUtil;

import java.awt.print.Pageable;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static stc.test.socialmedia.post.controller.PostRestController.FEED_URL;
import static stc.test.socialmedia.testdata.PostTestData.*;
import static stc.test.socialmedia.testdata.UserTestData.admin;
import static stc.test.socialmedia.testdata.UserTestData.user;

class PostRestControllerTest extends AbstractControllerTest{


    @Test
    void create() throws Exception {
        performJwt(MockMvcRequestBuilders.post(PostRestController.REST_URL+"?header=ss&description=s&file=")
                .contentType(new MediaType("multipart","form-data")),user)
                .andExpect(status().isOk())
                .andExpect(POST_MATCHER.contentJson(getNew()));
    }

    @Test
    void createInvalid() throws Exception {
        performJwt(MockMvcRequestBuilders.post(PostRestController.REST_URL+"?header=s&description=s&file=")
                .contentType(new MediaType("multipart","form-data")),user)
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void createWithNotSpecifiedContentType() throws Exception {
        performJwt(MockMvcRequestBuilders.post(PostRestController.REST_URL+"?header=sss&description=s&file="),user)
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    void updateWithNotSpecifiedContentType() throws Exception {
        performJwt(MockMvcRequestBuilders.put(PostRestController.REST_URL+"/1?header=sss&description=s&file="),admin)
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    void update() throws Exception {
        performJwt(MockMvcRequestBuilders.put(PostRestController.REST_URL+"/1?header=sss")
                .contentType(new MediaType("multipart","form-data")),admin)
                .andExpect(status().isOk())
                .andExpect(POST_MATCHER.contentJson(getUpdated()));
    }

    @Test
    void delete() throws Exception {
        performJwt(MockMvcRequestBuilders.delete(PostRestController.REST_URL+"/1"),admin)
                .andExpect(status().isOk());
    }
    @Test
    void deleteNotOwn() throws Exception {
        performJwt(MockMvcRequestBuilders.delete(PostRestController.REST_URL+"/1"),user)
                .andExpect(status().isUnprocessableEntity());
    }
}