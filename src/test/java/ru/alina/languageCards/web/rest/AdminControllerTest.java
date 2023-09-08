package ru.alina.languageCards.web.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.alina.languageCards.RoleData;
import ru.alina.languageCards.UserData;
import ru.alina.languageCards.dto.AuthRequest;
import ru.alina.languageCards.model.Status;
import ru.alina.languageCards.model.User;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.alina.languageCards.JsonUtil.*;

class AdminControllerTest extends ControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAll() throws Exception{
        MvcResult result =   this.mockMvc.perform(get("/admin/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        UserData.match(UserData.USERS, jsonArrToObject(result.getResponse().getContentAsString(), User.class));
    }


    @Test
    void getUser() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/admin/"+UserData.USER_1.getId()))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        User user = jsonToObject(result.getResponse().getContentAsString(), User.class);
        UserData.match(user, UserData.USER_1);
        RoleData.match(user.getRoles(), RoleData.rolesUser1);
    }

    @Test
    void createAdmin() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/admin/create/administrator")
                        .content(asJsonString(new AuthRequest(UserData.getNew().getUsername(), UserData.getNew().getPassword())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        User created = jsonToObject(result.getResponse().getContentAsString(), User.class);
        User actual = UserData.getNew();
        actual.setId(created.getId());
        actual.setStatus(Status.ACTIVE);
        UserData.matchExPass(actual, created);
        RoleData.match(created.getRoles(), Set.of(RoleData.roleAdmin));
    }

    @Test
    void update() throws Exception {
       this.mockMvc.perform(put("/admin/update")
                       .content(asJsonString(UserData.getUpdated()))
                       .contentType(MediaType.APPLICATION_JSON)
                       .accept(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andExpect(status().isNoContent());
    }

    @Test
    void deleteUser() throws Exception {
        this.mockMvc.perform(delete("/admin/" + UserData.USER_1.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}
