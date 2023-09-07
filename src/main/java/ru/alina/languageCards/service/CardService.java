package ru.alina.languageCards.service;

import ru.alina.languageCards.model.Card;

import java.util.List;


public interface CardService {
    Card get(Long id, Long userId);

    List<Card> getAll(Long userId);

    Card create(Card card, Long userId);

    void update(Card card, Long userId);
    void delete(Long id, Long userId);
}
