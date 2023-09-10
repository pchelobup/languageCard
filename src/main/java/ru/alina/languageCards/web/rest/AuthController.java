package ru.alina.languageCards.web.rest;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alina.languageCards.dto.AuthRequest;
import ru.alina.languageCards.dto.JwtResponse;
import ru.alina.languageCards.exception.ControllerErrorMessage;
import ru.alina.languageCards.exception.NotFoundException;
import ru.alina.languageCards.exception.TokenRefreshException;
import ru.alina.languageCards.jwt.JwtTokenUtils;
import ru.alina.languageCards.jwt.JwtUserDetailsService;
import ru.alina.languageCards.jwt.UserDetailsImpl;
import ru.alina.languageCards.jwt.dto.TokenRefreshRequest;
import ru.alina.languageCards.jwt.dto.TokenRefreshResponse;
import ru.alina.languageCards.model.RefreshToken;
import ru.alina.languageCards.model.Status;
import ru.alina.languageCards.model.User;
import ru.alina.languageCards.service.UserService;
import ru.alina.languageCards.service.impl.RefreshTokenService;

import java.time.Instant;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);


    private final AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtTokenUtils jwtTokenUtils;

    private final UserService userService;

    private final JwtUserDetailsService jwtUserDetailsService;

    private final RefreshTokenService refreshTokenService;


    public AuthController(AuthenticationManager authenticationManager, BCryptPasswordEncoder passwordEncoder, JwtTokenUtils jwtTokenUtils, UserService userService, JwtUserDetailsService jwtUserDetailsService, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtils = jwtTokenUtils;
        this.userService = userService;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody AuthRequest request) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
        } catch (BadCredentialsException | NotFoundException | InternalAuthenticationServiceException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(new ControllerErrorMessage(HttpStatus.UNAUTHORIZED.value(), "incorrect login or password"), HttpStatus.UNAUTHORIZED);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ControllerErrorMessage(HttpStatus.UNAUTHORIZED.value(), "auth exception"), HttpStatus.UNAUTHORIZED);
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String token = jwtTokenUtils.generateToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());
        log.info("IN createAuthToken user with name  {} log in", userDetails.getUsername());
        return ResponseEntity.ok(new JwtResponse(token, refreshToken.getToken()));

    }

    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody @Valid AuthRequest registrationUser) {
        User user = new User(registrationUser.getUserName(), passwordEncoder.encode(registrationUser.getPassword()), Instant.now(), Status.ACTIVE);
        User created = userService.create(user);
        log.info("IN createNewUse user with id {} was registered ", created.id());
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    UserDetailsImpl userDetails = (UserDetailsImpl) jwtUserDetailsService.loadUserByUsername(user.getUsername());
                    String token = jwtTokenUtils.generateToken(userDetails);
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

}
