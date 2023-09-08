package ru.alina.languageCards.web.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import ru.alina.languageCards.AuthData;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class UserControllerTest extends ControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Test
    void enabled() throws Exception {
        this.mockMvc.perform(put("/user/delete").header(AuthData.authHeader, AuthData.tokenUser1))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}