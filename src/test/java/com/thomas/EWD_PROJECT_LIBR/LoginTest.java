package com.thomas.EWD_PROJECT_LIBR;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = EwdProjectLibrApplication.class)
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
@RunWith(SpringRunner.class)
class LoginTest {

    @Autowired
    private MockMvc mockMvc;
    private LoginController controller;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
    @Test
    public void getLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }
}
