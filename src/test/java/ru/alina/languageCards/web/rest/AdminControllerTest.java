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

import java.util.HashSet;
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
    void getUserNotExist() throws Exception {
        this.mockMvc.perform(get("/admin/"+ UserData.ID_NOT_FOUND))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
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
    void createAdminBadRequest() throws Exception {
        this.mockMvc.perform(post("/admin/create/administrator")
                        .content(asJsonString(new AuthRequest(UserData.getNew().getUsername(), "")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(post("/admin/create/administrator")
                        .content(asJsonString(new AuthRequest(" ", UserData.getNew().getPassword())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void update() throws Exception {
        User updated = UserData.getUpdated();
        updated.setRoles(RoleData.rolesUser1);
       this.mockMvc.perform(put("/admin/update")
                       .content(asJsonString(updated))
                       .contentType(MediaType.APPLICATION_JSON)
                       .accept(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andExpect(status().isNoContent());
    }



    @Test
    void updateBadRequest() throws Exception {

        this.mockMvc.perform(put("/admin/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateBadRequest2() throws Exception {
        this.mockMvc.perform(put("/admin/update")
                        .content(asJsonString(new User(null, UserData.USER_1.getUsername(), UserData.USER_1.getPassword(), UserData.USER_1.getRegistered(), UserData.USER_1.getStatus(), RoleData.rolesUser1)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateBadRequest3() throws Exception {

        this.mockMvc.perform(put("/admin/update")
                        .content(asJsonString(new User(UserData.USER_1.getId(), null, UserData.USER_1.getPassword(), UserData.USER_1.getRegistered(), UserData.USER_1.getStatus(), RoleData.rolesUser1)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateBadRequest4() throws Exception {

        this.mockMvc.perform(put("/admin/update")
                        .content(asJsonString(new User(UserData.USER_1.getId(), "  ", UserData.USER_1.getPassword(), UserData.USER_1.getRegistered(), UserData.USER_1.getStatus(), RoleData.rolesUser1)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateBadRequest5() throws Exception {

        this.mockMvc.perform(put("/admin/update")
                        .content(asJsonString(new User(UserData.USER_1.getId(), UserData.USER_1.getUsername(), null, UserData.USER_1.getRegistered(), UserData.USER_1.getStatus(), RoleData.rolesUser1)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateBadRequest6() throws Exception {

        this.mockMvc.perform(put("/admin/update")
                        .content(asJsonString(new User(UserData.USER_1.getId(), UserData.USER_1.getUsername(), "  ", UserData.USER_1.getRegistered(), UserData.USER_1.getStatus(), RoleData.rolesUser1)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateBadRequest7() throws Exception {

        this.mockMvc.perform(put("/admin/update")
                        .content(asJsonString(new User(UserData.USER_1.getId(), UserData.USER_1.getUsername(), UserData.USER_1.getPassword(), null, UserData.USER_1.getStatus(), RoleData.rolesUser1)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateBadRequest8() throws Exception {

        this.mockMvc.perform(put("/admin/update")
                        .content(asJsonString(new User(UserData.USER_1.getId(), UserData.USER_1.getUsername(), UserData.USER_1.getPassword(), UserData.USER_1.getRegistered(), null, RoleData.rolesUser1)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateBadRequest9() throws Exception {

        this.mockMvc.perform(put("/admin/update")
                        .content(asJsonString(new User(UserData.USER_1.getId(), UserData.USER_1.getUsername(), UserData.USER_1.getPassword(), UserData.USER_1.getRegistered(), UserData.USER_1.getStatus(), null)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateBadRequest10() throws Exception {

        this.mockMvc.perform(put("/admin/update")
                        .content(asJsonString(new User(UserData.USER_1.getId(), UserData.USER_1.getUsername(), UserData.USER_1.getPassword(), UserData.USER_1.getRegistered(), UserData.USER_1.getStatus(), new HashSet<>())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
    @Test
    void deleteUser() throws Exception {
        this.mockMvc.perform(delete("/admin/" + UserData.USER_1.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteUserNotExist() throws Exception {
        this.mockMvc.perform(delete("/admin/"+ UserData.ID_NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}
