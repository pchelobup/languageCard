package ru.alina.languageCards.service;

import ru.alina.languageCards.model.User;

import java.util.List;

public interface UserService {
    User get(Long id);

    List<User> getAll();

    User create(User user);

    void update(User user);
    void delete(Long id);
}
