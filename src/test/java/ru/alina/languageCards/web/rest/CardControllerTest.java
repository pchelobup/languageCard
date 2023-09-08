package ru.alina.languageCards.web.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.alina.languageCards.AuthData;
import ru.alina.languageCards.CardData;
import ru.alina.languageCards.JsonUtil;
import ru.alina.languageCards.dto.CardTo;
import ru.alina.languageCards.model.Card;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CardControllerTest extends ControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void getCard() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/card/" + CardData.CARD_1.getId())
                .header(AuthData.authHeader, AuthData.tokenUser1))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        CardData.match(JsonUtil.jsonToObject(result.getResponse().getContentAsString(), Card.class), CardData.CARD_1);
    }

    @Test
    void getAll() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/card")
                        .header(AuthData.authHeader, AuthData.tokenUser1))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        CardData.match(JsonUtil.jsonArrToObject(result.getResponse().getContentAsString(), Card.class), CardData.CARDS_USER_1);
    }

    @Test
    void create() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/card/create")
                        .header(AuthData.authHeader, AuthData.tokenUser1)
                        .content(JsonUtil.asJsonString(CardData.getNew())).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        Card created = JsonUtil.jsonToObject(result.getResponse().getContentAsString(), Card.class);
        Card card = CardData.getNew();
        card.setId(created.getId());
        CardData.match(created, card);
    }

    @Test
    void update() throws Exception {
        CardTo updated = new CardTo(CardData.CARD_1.getId(),"test", "тест");
        this.mockMvc.perform(put("/card/update")
                        .header(AuthData.authHeader, AuthData.tokenUser1)
                        .content(JsonUtil.asJsonString(updated))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteCard() throws Exception {
        this.mockMvc.perform(delete("/card/delete/" + CardData.CARD_1.getId()).
                        header(AuthData.authHeader, AuthData.tokenUser1))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}