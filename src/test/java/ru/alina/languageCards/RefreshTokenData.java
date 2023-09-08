package ru.alina.languageCards;

import ru.alina.languageCards.model.RefreshToken;

import java.time.LocalDate;
import java.time.ZoneOffset;

public class RefreshTokenData {
    public static RefreshToken REFRESH_TOKEN = new RefreshToken(1L, "282f77f2-1bcb-4ea2-ae91-d1a28c30e9fc", LocalDate.of(2023, 8, 4).atStartOfDay().toInstant(ZoneOffset.UTC));

    public static void match(RefreshToken actual, RefreshToken expected) {
        Matcher.match(actual, expected, "user");
    }

}
