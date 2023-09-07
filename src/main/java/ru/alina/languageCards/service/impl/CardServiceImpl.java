package ru.alina.languageCards.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.alina.languageCards.exception.NotFoundException;
import ru.alina.languageCards.model.Card;
import ru.alina.languageCards.model.Level;
import ru.alina.languageCards.repository.CardRepository;
import ru.alina.languageCards.repository.UserRepository;
import ru.alina.languageCards.service.CardService;
import ru.alina.languageCards.util.ValidationUtil;

import java.util.List;

import static ru.alina.languageCards.util.ValidationUtil.checkNotFoundWithId;

@Service
@Transactional(readOnly = true)
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;

    private final UserRepository userRepository;

    public CardServiceImpl(CardRepository cardRepository, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Card get(Long id, Long userId) {
        Assert.notNull(id, "Id can not be null");
       return cardRepository.findById(id)
                .stream()
                .filter(c -> c.getUser().getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(String.format("Card with id %s for user with id %s does not exist", id, userId)));
    }

    @Override
    public List<Card> getAll(Long userId) {
        Assert.notNull(userId, "Id can not be null");
        return cardRepository.getAll(userId);
    }

    @Override
    @Transactional
    public Card create(Card card, Long userId) {
        ValidationUtil.isNew(card);
        card.setUser(userRepository.getReferenceById(userId));
        card.setLevel(Level.ONE);
        return cardRepository.save(card);
    }

    @Override
    @Transactional
    public void update(Card card, Long userId) {
        Assert.notNull(userId, "Id can not be null");
        //check if this cars belong this user. in the future we can take userId from jwt and delete this chekc
        get(card.getId(), userId);
        card.setUser(userRepository.getReferenceById(userId));
        checkNotFoundWithId(cardRepository.save(card), card.getId());
    }

    @Override
    @Transactional
    public void delete(Long id, Long userId) {
        if (cardRepository.delete(id, userId) == 0) throw new NotFoundException(String.format("card with id %s and userId %s did not exist", id, userId));
    }
}
