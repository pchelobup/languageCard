package ru.alina.languageCards.web.rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.alina.languageCards.RoleData;
import ru.alina.languageCards.UserData;
import ru.alina.languageCards.dto.AuthRequest;
import ru.alina.languageCards.dto.JwtResponse;
import ru.alina.languageCards.model.Status;
import ru.alina.languageCards.model.User;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.alina.languageCards.JsonUtil.asJsonString;
import static ru.alina.languageCards.JsonUtil.jsonToObject;

class AuthControllerTest extends ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createAuthToken() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/auth/login")
                        .content(asJsonString(UserData.AUTH_REQUEST_REGISTERED))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        JwtResponse jwtResponse = jsonToObject(result.getResponse().getContentAsString(), JwtResponse.class);
        Assertions.assertNotNull(jwtResponse);
        Assertions.assertNotNull(jwtResponse.getToken());
        Assertions.assertNotNull(jwtResponse.getRefreshToken());

    }

    @Test
    void createAuthTokenUserNotExist() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/auth/login")
                        .content(asJsonString(UserData.AUTH_REQUEST_REGISTERED))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        JwtResponse jwtResponse = jsonToObject(result.getResponse().getContentAsString(), JwtResponse.class);
        Assertions.assertNotNull(jwtResponse);
        Assertions.assertNotNull(jwtResponse.getToken());
        Assertions.assertNotNull(jwtResponse.getRefreshToken());

    }

    @Test
    void createNewUser() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/auth/registration")
                        .content(asJsonString(UserData.AUTH_REQUEST_NEW))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        User created = jsonToObject(result.getResponse().getContentAsString(), User.class);
        User expected = UserData.getNew();
        expected.setId(created.getId());
        expected.setStatus(Status.ACTIVE);
        UserData.matchExPass(created, expected);

        RoleData.match(created.getRoles(), Set.of(RoleData.roleUser));
    }

    @Test
    void createNewUserBadRequest1() throws Exception {
        this.mockMvc.perform(post("/auth/registration")
                        .content(asJsonString(new AuthRequest(UserData.getNew().getUsername(), "  ")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewUserBadRequest2() throws Exception {
        this.mockMvc.perform(post("/auth/registration")
                        .content(asJsonString(new AuthRequest("  ", UserData.getNew().getPassword())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewUserBadRequest3() throws Exception {
        this.mockMvc.perform(post("/auth/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}