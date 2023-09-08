package ru.alina.languageCards.web.rest;

import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alina.languageCards.dto.CardRequestCreateTo;
import ru.alina.languageCards.dto.CardTo;
import ru.alina.languageCards.jwt.JwtTokenUtils;
import ru.alina.languageCards.model.Card;
import ru.alina.languageCards.service.CardService;

@RestController
@RequestMapping(value = "/card")
public class CardController {
    private final CardService cardService;

    private final JwtTokenUtils jwtTokenUtils;

    private final ModelMapper modelMapper;

    public CardController(CardService cardService, JwtTokenUtils jwtTokenUtils, ModelMapper modelMapper) {
        this.cardService = cardService;
        this.jwtTokenUtils = jwtTokenUtils;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id, HttpServletRequest request) {
        Long userId = jwtTokenUtils.getUserId(request);
        return new ResponseEntity<>(cardService.get(id, userId), HttpStatus.OK );
    }

    @GetMapping()
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        Long userId = jwtTokenUtils.getUserId(request);
        return new ResponseEntity<>(cardService.getAll(userId), HttpStatus.OK );
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CardRequestCreateTo cardRequestCreateTo, HttpServletRequest request) {
        Card card = modelMapper.map(cardRequestCreateTo, Card.class);
        return new ResponseEntity<>(modelMapper.map(cardService.create(card, jwtTokenUtils.getUserId(request)), CardTo.class), HttpStatus.CREATED );
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody CardTo cardTo, HttpServletRequest request) {
        Card card = cardService.get(cardTo.getId(), jwtTokenUtils.getUserId(request));
        card.setWord(cardTo.getWord());
        card.setTranslation(card.getTranslation());
        cardService.update(card, jwtTokenUtils.getUserId(request));
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id, HttpServletRequest request) {
        cardService.delete(id, jwtTokenUtils.getUserId(request));
    }
}
