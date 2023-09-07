package ru.alina.languageCards.service;


import ru.alina.languageCards.model.ERole;
import ru.alina.languageCards.model.Role;

public interface RoleService {
    Role findByName(ERole name);


}
