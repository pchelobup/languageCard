package ru.alina.languageCards.web.rest;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.alina.languageCards.jwt.JwtTokenUtils;
import ru.alina.languageCards.service.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private final UserService userService;

    private final JwtTokenUtils jwtTokenUtils;

    public UserController(UserService userService, JwtTokenUtils jwtTokenUtils) {
        this.userService = userService;
        this.jwtTokenUtils = jwtTokenUtils;
    }

    @PutMapping(value = "/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enabled(HttpServletRequest request) {
        userService.enabled(jwtTokenUtils.getUserId(request));
    }


}
