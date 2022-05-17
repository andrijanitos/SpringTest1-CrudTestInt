package com.andrijanica.SpringTest1.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static net.bytebuddy.utility.RandomString.make;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GreetingControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    public void greetingTest_withoutParamOk() {
        final String greetingDefaultMessage = "Hello World";

        String result = mockMvc.perform(get("/greeting"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertNotNull(result);
        assertEquals(result, greetingDefaultMessage);
    }

    @Test
    @SneakyThrows
    public void greetingTest_withParamOk() {
        final String prefix = "Hello ";
        final String param = make(10);
        String result = mockMvc.perform(get("/greeting?name=" + param))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertNotNull(result);
        assertEquals(result, prefix + param);
    }
}
