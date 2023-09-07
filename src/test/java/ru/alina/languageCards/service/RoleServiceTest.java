package ru.alina.languageCards.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.alina.languageCards.model.ERole;

import static ru.alina.languageCards.RoleData.match;
import static ru.alina.languageCards.RoleData.roleUser;

class RoleServiceTest extends ServiceTest {
    @Autowired
    RoleService roleService;
    @Test
    void findByName() {
        match(roleService.findByName(ERole.USER), roleUser);
    }
}