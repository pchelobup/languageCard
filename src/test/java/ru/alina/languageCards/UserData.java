package ru.alina.languageCards;

import ru.alina.languageCards.dto.AuthRequest;
import ru.alina.languageCards.model.Status;
import ru.alina.languageCards.model.User;

import java.time.Instant;
import java.util.List;

public class UserData {

    public static long ID_NOT_FOUND = 555;
    public static User USER_1 = new User(1L, "Masha", "$2a$12$ZpXbje4hlSVkb/9raerot.ud0Odzjt9dQS7j6x/nISd2deiHc9cvi", Instant.parse("2023-09-30T10:00:00Z"), Status.ACTIVE);
    public static User USER_2 = new User(2L, "Glasha", "$2a$12$ZpXbje4hlSVkb/9raerot.ud0Odzjt9dQS7j6x/nISd2deiHc9cvi", Instant.parse("2023-01-30T10:00:00Z"), Status.ACTIVE);

    public static List<User> USERS = List.of(USER_1, USER_2);

    public static final AuthRequest AUTH_REQUEST_NEW = new AuthRequest(getNew().getUsername(), "1234");

    public static final AuthRequest AUTH_REQUEST_REGISTERED = new AuthRequest(USER_1.getUsername(), "1234");

    public static User getNew() {
        return new User("Petya", "1234");
    }

    public static User getUpdated() {
        return new User(USER_1.getId(), USER_1.getUsername(), USER_1.getPassword(),Instant.now(),Status.DELETED, RoleData.rolesUser1);
    }

    public static void match(User actual, User expected) {
        Matcher.match(actual, expected, "roles");
    }

    public static void matchExRegistered(User actual, User expected) {
        Matcher.match(actual, expected, "roles", "registered");
    }

    public static void matchExPass(User actual, User expected) {
        Matcher.match(actual, expected, "roles", "registered", "password");
    }

    public static void match(List<User> actual, List<User> expected) {
        Matcher.match(actual, expected,  "roles");
    }
}
