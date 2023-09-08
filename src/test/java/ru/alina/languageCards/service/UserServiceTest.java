package ru.alina.languageCards.service;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.alina.languageCards.exception.NotFoundException;
import ru.alina.languageCards.exception.UnsuitableEntity;
import ru.alina.languageCards.model.Status;
import ru.alina.languageCards.model.User;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.alina.languageCards.UserData.*;


class UserServiceTest extends ServiceTest {
    @Autowired
    UserService userService;

    @Test
    void get() {
        match(userService.get(USER_1.getId()), USER_1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> userService.get(ID_NOT_FOUND));
    }

    @Test
    void getByUserName() {
        match(userService.getByUserName(USER_1.getUsername()), USER_1);
    }

    @Test
    void getByUserNameNotFound() {
        assertThrows(NotFoundException.class, () -> userService.getByUserName(getNew().getUsername()));
    }
    @Test
    void getAll() {
        match(userService.getAll(), USERS);
    }

    @Test
    void create() {
        User created = userService.create(getNew());
        long newId = created.id();
        User newUser = getNew();
        newUser.setId(newId);
        newUser.setRegistered(created.getRegistered());
        newUser.setStatus(Status.ACTIVE);
        match(created, newUser);
        match(userService.get(newId), newUser);
    }

    @Test
    void createAlreadyExist() {
        assertThrows(UnsuitableEntity.class, () -> userService.create(USER_1));
    }

    @Test
    void createWithException() {
        validateRootCause(ConstraintViolationException.class, () -> userService.create(new User(" ", "1234")));
        validateRootCause(ConstraintViolationException.class, () -> userService.create(new User(null, "1234")));
        validateRootCause(ConstraintViolationException.class, () -> userService.create(new User("new", "    ")));
        validateRootCause(ConstraintViolationException.class, () -> userService.create(new User("new", null)));
    }


    @Test
    void update() {
        User updated = getUpdated();
        userService.update(updated);
        matchExRegistered(userService.get(USER_1.getId()), getUpdated());

    }

    @Test
    void updateNew() {
        assertThrows(UnsuitableEntity.class, () -> userService.update(getNew()));
    }

    @Test
    void UpdateWithException() {
        validateRootCause(ConstraintViolationException.class, () -> userService.update(new User(USER_1.getId(), " ", USER_1.getPassword(), Instant.now(), Status.ACTIVE)));
        validateRootCause(ConstraintViolationException.class, () -> userService.update(new User(USER_1.getId(), null, USER_1.getPassword(), Instant.now(), Status.ACTIVE)));
        validateRootCause(ConstraintViolationException.class, () -> userService.update(new User(USER_1.getId(), USER_1.getUsername(), "   ", Instant.now(), Status.ACTIVE)));
        validateRootCause(ConstraintViolationException.class, () -> userService.update(new User(USER_1.getId(), USER_1.getUsername(), null, Instant.now(), Status.ACTIVE)));
        validateRootCause(IllegalArgumentException.class, () -> userService.update(new User(USER_1.getId(), USER_1.getUsername(), USER_1.getPassword(), null, Status.ACTIVE)));
        validateRootCause(IllegalArgumentException.class, () -> userService.update(new User(USER_1.getId(), USER_1.getUsername(), USER_1.getPassword(), Instant.now(), null)));
    }

    @Test
    void delete() {
        userService.delete(USER_1.id());
        assertThrows(NotFoundException.class, () -> userService.get(USER_1.getId()));
    }

    @Test
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> userService.delete(ID_NOT_FOUND));
    }
}