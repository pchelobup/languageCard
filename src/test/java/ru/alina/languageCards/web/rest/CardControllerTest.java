package ru.alina.languageCards.web.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.alina.languageCards.AuthData;
import ru.alina.languageCards.JsonUtil;
import ru.alina.languageCards.dto.CardRequestCreateTo;
import ru.alina.languageCards.dto.CardTo;
import ru.alina.languageCards.model.Card;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.alina.languageCards.CardData.*;

class CardControllerTest extends ControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void getCard() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/card/" + CARD_1.getId())
                        .header(AuthData.authHeader, AuthData.tokenUser1))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        match(JsonUtil.jsonToObject(result.getResponse().getContentAsString(), Card.class), CARD_1);
    }

    @Test
    void getCardNotFound() throws Exception {
        this.mockMvc.perform(get("/card/" + ID_NOT_FOUND)
                        .header(AuthData.authHeader, AuthData.tokenUser1))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getCardNotOwn() throws Exception {
        this.mockMvc.perform(get("/card/" + CARD_12.getId())
                        .header(AuthData.authHeader, AuthData.tokenUser1))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getAll() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/card")
                        .header(AuthData.authHeader, AuthData.tokenUser1))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        match(JsonUtil.jsonArrToObject(result.getResponse().getContentAsString(), Card.class), CARDS_USER_1);
    }

    @Test
    void create() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/card/create")
                        .header(AuthData.authHeader, AuthData.tokenUser1)
                        .content(JsonUtil.asJsonString(getNew())).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        Card created = JsonUtil.jsonToObject(result.getResponse().getContentAsString(), Card.class);
        Card card = getNew();
        card.setId(created.getId());
        match(created, card);
    }

    @Test
    void createBadRequest() throws Exception {
        this.mockMvc.perform(post("/card/create")
                        .header(AuthData.authHeader, AuthData.tokenUser1)
                        .content(JsonUtil.asJsonString(new CardRequestCreateTo(" ", getNew().getTranslation(), LocalDate.now())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void createBadRequest2() throws Exception {
        this.mockMvc.perform(post("/card/create")
                        .header(AuthData.authHeader, AuthData.tokenUser1)
                        .content(JsonUtil.asJsonString(new CardRequestCreateTo(null, getNew().getTranslation(), LocalDate.now())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void createBadRequest3() throws Exception {
        this.mockMvc.perform(post("/card/create")
                        .header(AuthData.authHeader, AuthData.tokenUser1)
                        .content(JsonUtil.asJsonString(new CardRequestCreateTo(getNew().getWord(), " ", LocalDate.now())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void createBadRequest4() throws Exception {
        this.mockMvc.perform(post("/card/create")
                        .header(AuthData.authHeader, AuthData.tokenUser1)
                        .content(JsonUtil.asJsonString(new CardRequestCreateTo(getNew().getWord(), null, LocalDate.now())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void createBadRequest5() throws Exception {
        this.mockMvc.perform(post("/card/create")
                        .header(AuthData.authHeader, AuthData.tokenUser1)
                        .content(JsonUtil.asJsonString(new CardRequestCreateTo(getNew().getWord(), getNew().getTranslation(), null)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void update() throws Exception {
        this.mockMvc.perform(put("/card/update")
                        .header(AuthData.authHeader, AuthData.tokenUser1)
                        .content(JsonUtil.asJsonString(getUpdatedCardTo()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void updateBadRequest() throws Exception {
        this.mockMvc.perform(put("/card/update")
                        .header(AuthData.authHeader, AuthData.tokenUser1)
                        .content(JsonUtil.asJsonString(new CardTo(null, getUpdatedCardTo().getWord(), getUpdatedCardTo().getTranslation())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateBadRequest2() throws Exception {
        this.mockMvc.perform(put("/card/update")
                        .header(AuthData.authHeader, AuthData.tokenUser1)
                        .content(JsonUtil.asJsonString(new CardTo(ID_NOT_FOUND, getUpdatedCardTo().getWord(), getUpdatedCardTo().getTranslation())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void updateBadRequest3() throws Exception {
        this.mockMvc.perform(put("/card/update")
                        .header(AuthData.authHeader, AuthData.tokenUser1)
                        .content(JsonUtil.asJsonString(new CardTo(CARD_14.getId(), getUpdatedCardTo().getWord(), getUpdatedCardTo().getTranslation())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void updateBadRequest5() throws Exception {
        this.mockMvc.perform(put("/card/update")
                        .header(AuthData.authHeader, AuthData.tokenUser1)
                        .content(JsonUtil.asJsonString(new CardTo(getUpdatedCardTo().getId(), "  ", getUpdatedCardTo().getTranslation())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateBadRequest6() throws Exception {
        this.mockMvc.perform(put("/card/update")
                        .header(AuthData.authHeader, AuthData.tokenUser1)
                        .content(JsonUtil.asJsonString(new CardTo(getUpdatedCardTo().getId(), null, getUpdatedCardTo().getTranslation())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
    @Test
    void updateBadRequest7() throws Exception {
        this.mockMvc.perform(put("/card/update")
                        .header(AuthData.authHeader, AuthData.tokenUser1)
                        .content(JsonUtil.asJsonString(new CardTo(getUpdatedCardTo().getId(), getUpdatedCardTo().getWord(), null)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateBadRequest8() throws Exception {
        this.mockMvc.perform(put("/card/update")
                        .header(AuthData.authHeader, AuthData.tokenUser1)
                        .content(JsonUtil.asJsonString(new CardTo(getUpdatedCardTo().getId(), getUpdatedCardTo().getWord(), " ")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    void deleteCard() throws Exception {
        this.mockMvc.perform(delete("/card/delete/" + CARD_1.getId()).
                        header(AuthData.authHeader, AuthData.tokenUser1))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteCardNotOwn() throws Exception {
        this.mockMvc.perform(delete("/card/delete/" + CARD_14.getId()).
                        header(AuthData.authHeader, AuthData.tokenUser1))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}