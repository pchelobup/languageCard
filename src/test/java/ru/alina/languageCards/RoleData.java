package ru.alina.languageCards;


import ru.alina.languageCards.model.ERole;
import ru.alina.languageCards.model.Role;

import java.util.List;
import java.util.Set;

public class RoleData {
    public static Role roleUser = new Role(200, ERole.USER);
    public static Role roleAdmin = new Role(201, ERole.ADMIN);

    public static Set<Role> rolesUser1 = Set.of(roleUser, roleAdmin);

    public static final List<Role> roles = List.of(roleUser, roleAdmin);

    public static void match(Role actual, Role expected) {
        Matcher.match(actual, expected);
    }

    public static void match(Set<Role> actual, Set<Role> expected) {
        Matcher.match(actual, expected);
    }
}
