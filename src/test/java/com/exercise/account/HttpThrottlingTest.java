package com.exercise.account;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountApplication.class)
@WebAppConfiguration
public class HttpThrottlingTest {

    private final MediaType jsonContentType = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8")
    );


    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void testThrottledController() throws Exception {
        RequestPostProcessor postProcessor1 = request -> {
            request.setRemoteAddr("127.0.0.1");
            return request;
        };

        for (int i = 0; i < 100; i++) {
            mockMvc.perform(get("/api/account/add")
                    .with(postProcessor1))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(jsonContentType));
        }

        mockMvc.perform(get("/api/list1")
                .with(postProcessor1))
                .andExpect(status().is(429));

        Thread.sleep(1000);

        for (int i = 0; i < 100; i++) {
            mockMvc.perform(get("/api/account/add")
                    .with(postProcessor1))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(jsonContentType));
        }

        mockMvc.perform(get("/api/account/app")
                .with(postProcessor1))
                .andExpect(status().is(429));

    }

}