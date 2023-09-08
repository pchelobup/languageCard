package ru.alina.languageCards.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.alina.languageCards.UserData;
import ru.alina.languageCards.exception.TokenRefreshException;
import ru.alina.languageCards.model.RefreshToken;
import ru.alina.languageCards.service.impl.RefreshTokenService;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.alina.languageCards.RefreshTokenData.REFRESH_TOKEN;
import static ru.alina.languageCards.RefreshTokenData.match;

class RefreshTokenServiceTest extends ServiceTest {

    @Autowired
    RefreshTokenService refreshTokenService;

    @Test
    void findByToken() {
        RefreshToken refreshToken = refreshTokenService.findByToken(REFRESH_TOKEN.getToken()).orElse(null);
        match(refreshToken, REFRESH_TOKEN);
    }

    @Test
    void createRefreshTokenById() {
        Assertions.assertNotNull(refreshTokenService.createRefreshToken(UserData.USER_1.getId()));
    }

    @Test
    void createRefreshTokenByUserName() {
        Assertions.assertNotNull(refreshTokenService.createRefreshToken(UserData.USER_1.getUsername()));
    }

    @Test
    void verifyExpiration() {
        assertThrows(TokenRefreshException.class, () -> refreshTokenService.verifyExpiration(REFRESH_TOKEN));
        Assertions.assertNull(refreshTokenService.findByToken(REFRESH_TOKEN.getToken()).orElse(null));
    }

}