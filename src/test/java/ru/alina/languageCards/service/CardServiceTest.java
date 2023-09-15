package ru.alina.languageCards.service;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.alina.languageCards.exception.NotFoundException;
import ru.alina.languageCards.model.Card;
import ru.alina.languageCards.model.State;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.alina.languageCards.CardData.*;

class CardServiceTest extends ServiceTest {
    @Autowired
    CardService cardService;

    @Test
    void get() {
        match(cardService.get(CARD_1.getId(), USER1_ID), CARD_1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> cardService.get(ID_NOT_FOUND, USER1_ID));
    }

    @Test
    void getNotOwn() {
        assertThrows(NotFoundException.class, () -> cardService.get(CARD_1.getId(), USER_ID_NOT_FOUND));
    }

    @Test
    void getAll() {
        match(cardService.getAll(USER1_ID), CARDS_USER_1);
    }

    @Test
    void create() {
        Card created = cardService.create(getNew(), USER1_ID);
        long newId = created.id();
        Card newCard = getNew();
        newCard.setId(newId);
        newCard.setState(State.LEVEL_ONE);
        match(created, newCard);
        match(cardService.get(newId, USER1_ID), newCard);
    }

    @Test
    void createWithException() {
        validateRootCause(ConstraintViolationException.class, () -> cardService.create(new Card(null, getNew().getTranslation(), getNew().getLastTaught()), USER1_ID));
        validateRootCause(ConstraintViolationException.class, () -> cardService.create(new Card(" ", getNew().getTranslation(), getNew().getLastTaught()), USER1_ID));
        validateRootCause(ConstraintViolationException.class, () -> cardService.create(new Card(getNew().getWord(), null, getNew().getLastTaught()), USER1_ID));
        validateRootCause(ConstraintViolationException.class, () -> cardService.create(new Card(getNew().getWord(), " ", getNew().getLastTaught()), USER1_ID));
        validateRootCause(ConstraintViolationException.class, () -> cardService.create(new Card(getNew().getWord(), getNew().getTranslation(), null), USER1_ID));
    }

    @Test
    void update() {
        Card updated = getUpdated();
        cardService.update(updated, USER1_ID);
        match(cardService.get(CARD_1.getId(), USER1_ID), getUpdated());
    }

    @Test
    void updateNotOwn() {
        assertThrows(NotFoundException.class, () -> cardService.update(getUpdated(), USER2_ID));
    }

    @Test
    void updateWithException() {
        validateRootCause(ConstraintViolationException.class, () -> {
            Card updated = getUpdated();
            updated.setTranslation(null);
            cardService.update(updated, USER1_ID);

        });

        validateRootCause(ConstraintViolationException.class, () -> {
            Card updated = getUpdated();
            updated.setTranslation("   ");
            cardService.update(updated, USER1_ID);

        });

        validateRootCause(ConstraintViolationException.class, () -> {
            Card updated = getUpdated();
            updated.setWord(null);
            cardService.update(updated, USER1_ID);

        });

        validateRootCause(ConstraintViolationException.class, () -> {
            Card updated = getUpdated();
            updated.setWord("   ");
            cardService.update(updated, USER1_ID);

        });

        validateRootCause(ConstraintViolationException.class, () -> {
            Card updated = getUpdated();
            updated.setState(null);
            cardService.update(updated, USER1_ID);

        });
    }

    @Test
    void delete() {
        cardService.delete(CARD_1.getId(), USER1_ID);
        assertThrows(NotFoundException.class, () -> cardService.get(CARD_1.getId(), USER1_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> cardService.delete(ID_NOT_FOUND, USER1_ID));
    }

    @Test
    void deleteNotOwn() {
        assertThrows(NotFoundException.class, () -> cardService.delete(CARD_1.getId(), USER2_ID));
    }
}