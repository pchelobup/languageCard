package ru.alina.languageCards;

import ru.alina.languageCards.model.Card;
import ru.alina.languageCards.model.Level;

import java.time.LocalDate;
import java.util.List;

public class CardData {
    public static long ID_NOT_FOUND = 555;
    public static Card CARD_1 = new Card(1L, "name", "имя", Level.ONE);
    public static Card CARD_2 = new Card(2L, "value", "значение", Level.TWO);
    public static Card CARD_3 = new Card(3L, "instance", "экземпляр объекта", Level.SIX);
    public static Card CARD_4 = new Card(4L, "identifier", "идентификатор", Level.THREE);
    public static Card CARD_5 = new Card(5L, "digital", "цифровой", Level.THREE);
    public static Card CARD_6 = new Card(6L, "nomand", "кочевник", Level.THREE);
    public static Card CARD_7 = new Card(7L, "template", "шаблон", Level.FIVE);
    public static Card CARD_8 = new Card(8L, "timely", "своевременный", Level.ONE);
    public static Card CARD_9 = new Card(9L, "manage", "управлять", Level.SIX);
    public static Card CARD_10 = new Card(10L, "precede", "предшествовать", Level.TWO);
    public static Card CARD_11 = new Card(11L, "destruction", "уничтожение", Level.FOUR);
    public static Card CARD_12 = new Card(12L, "I", "я", Level.FOUR);
    public static Card CARD_13 = new Card(13L, "You", "ты", Level.FIVE);
    public static Card CARD_14 = new Card(14L, "We", "мы", Level.ONE);

    public static List<Card> CARDS_USER_1 = List.of(CARD_1, CARD_2, CARD_3, CARD_4, CARD_5, CARD_6, CARD_7, CARD_8, CARD_9, CARD_10, CARD_11);

    public static Long USER1_ID = UserData.USER_1.getId();
    public static Long USER2_ID = UserData.USER_2.getId();

    public static Long USER_ID_NOT_FOUND = UserData.ID_NOT_FOUND;
    public static Card getNew() {
        return new Card("new", "новый", LocalDate.now());
    }

    public static Card getUpdated() {
        return new Card(CARD_1.getId(), CARD_1.getWord(), CARD_1.getTranslation(), Level.TWO, LocalDate.now());
    }

    public static void match(Card actual, Card expected) {
        Matcher.match(actual, expected, "user", "lastTaught");
    }

    public static void match(List<Card> actual, List<Card> expected) {
        Matcher.match(actual, expected, "user", "lastTaught");
    }
}
