package ru.alina.languageCards.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.alina.languageCards.exception.NotFoundException;
import ru.alina.languageCards.exception.WrongRoleNameException;
import ru.alina.languageCards.model.ERole;
import ru.alina.languageCards.model.Role;
import ru.alina.languageCards.model.Status;
import ru.alina.languageCards.model.User;
import ru.alina.languageCards.repository.RoleRepository;
import ru.alina.languageCards.repository.UserRepository;
import ru.alina.languageCards.service.UserService;
import ru.alina.languageCards.util.ValidationUtil;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import static ru.alina.languageCards.util.ValidationUtil.checkNotFound;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User get(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("User with id %s did not found", id)));
    }

    @Override
    public User getByUserName(String userName) {
        return userRepository.findByUsername(userName).orElseThrow(() -> new NotFoundException(String.format("User with name %s does not exist", userName)));
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User create(User user) {
        Assert.notNull(user, "user is null");
        ValidationUtil.isNew(user);
        user.setRegistered(Instant.now());
        user.setStatus(Status.ACTIVE);
        if (user.getRoles() == null || user.getRoles().size()==0) {
            Role userRole = roleRepository.findByName(ERole.USER).orElseThrow(() -> new WrongRoleNameException("Role does not exist in table"));
            user.setRoles(Set.of(roleRepository.getReferenceById(userRole.getId())));
        }
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(User user) {
        Assert.notNull(user, "user is null");
        ValidationUtil.isNotNew(user);
        Assert.notNull(user.getRegistered(), "registered time can not be null");
        Assert.notNull(user.getStatus(), "status can not be null");
        userRepository.save(user);

    }

    @Override
    @Transactional
    public void delete(Long id) {
        checkNotFound(userRepository.delete(id)!= 0, id.toString());
    }
}
