package com.jumbo.store.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumbo.store.payload.LoginRequest;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JumboStoreControllerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(JumboStoreControllerTest.class);
    @Autowired
    WebApplicationContext context;

    ObjectMapper mapper;
    MockMvc mockMvc;

    private final MediaType MEDIA_TYPE_JSON_UTF8 =
            new MediaType("application", "json", StandardCharsets.UTF_8);

    private String accessToken;

    @BeforeAll
    void setUp() throws Exception {

        mapper = new ObjectMapper();
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        /* Login and get token for admin user*/
        MockHttpServletRequestBuilder request = post("/api/v1/auth/login");
        request.content(mapper.writeValueAsString(new LoginRequest("admin", "123456")));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.accessToken").exists())
                .andReturn().getResponse();
        JSONObject jsonObject = new JSONObject(response.getContentAsString());
        accessToken = jsonObject.get("accessToken") + "";
    }

    @Test
    void findNearestStoresToAGivenLocation() throws Exception {
        //Get nearest 5 location to lon = 6 and lat= 50
        MockHttpServletRequestBuilder request = get("/api/v1/store/near")
                .header("Authorization", "Bearer " + accessToken);
        request.param("longitude", "6");
        request.param("latitude", "50");
        request.param("limit", "5");
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.[0].id").exists())
                .andExpect(jsonPath("$.[1].id").exists())
                .andExpect(jsonPath("$.[2].id").exists())
                .andExpect(jsonPath("$.[3].id").exists())
                .andExpect(jsonPath("$.[4].id").exists())
                .andExpect(jsonPath("$.[0].longitude").exists())
                .andExpect(jsonPath("$.[0].latitude").exists())
                .andExpect(jsonPath("$.[0].city").exists());
    }

    @AfterAll
    void cleanup() throws Exception {
        LOGGER.error("Test finished...");
    }

}