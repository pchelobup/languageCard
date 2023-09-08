package ru.alina.languageCards.web.rest;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.alina.languageCards.dto.AuthRequest;
import ru.alina.languageCards.model.ERole;
import ru.alina.languageCards.model.Role;
import ru.alina.languageCards.model.User;
import ru.alina.languageCards.service.RoleService;
import ru.alina.languageCards.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private static final Logger log = LoggerFactory.getLogger(AdminController.class);
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserService userService;

    private final RoleService roleService;


    public AdminController(BCryptPasswordEncoder passwordEncoder, UserService userService, RoleService roleService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public List<User> getAll() {
        return userService.getAll();
    }

    /**
     *
     * @param id id of user
     * @return status.ok if ok, 404 - if not found
     */

    @GetMapping("/{id}")
    public User get(@PathVariable long id) {
        return userService.get(id);
    }

    @PostMapping("/create/administrator")
    public ResponseEntity<?> createAdmin(@RequestBody @Valid AuthRequest registrationUser) {
        Role role = roleService.findByName(ERole.ADMIN);
        User user = new User(registrationUser.getUserName(), passwordEncoder.encode(registrationUser.getPassword()), role);
        User created = userService.create(user);
        log.info("IN createNewUse user with id {} was registered ", created.id());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody User user) {
        userService.update(user);
        log.info("IN update admin updated user with id {}", user.id());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
        log.info("IN delete admin deleted user with id {}", id);
    }


}
