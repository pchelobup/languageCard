package ru.alina.languageCards.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alina.languageCards.exception.WrongRoleNameException;
import ru.alina.languageCards.model.ERole;
import ru.alina.languageCards.model.Role;
import ru.alina.languageCards.repository.RoleRepository;
import ru.alina.languageCards.service.RoleService;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(ERole erole) {
        return roleRepository.findByName(erole).orElseThrow(() -> new WrongRoleNameException("Role does not exist in table"));
    }
}
