package com.portfolio.moveandgroove.user.controller;

import com.portfolio.moveandgroove.user.model.User;
import com.portfolio.moveandgroove.user.repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private final UserRepository userRepository;

    public UserController(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(path = "/users/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable("id") final Long userId) {
        final String message = String.format("%s doesn't exist", userId);
        final Optional<User> foundUserById = Optional.ofNullable(userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(message)));
        return ResponseEntity.ok(foundUserById);
    }

    @GetMapping(path = "/users")
    public ResponseEntity<List<User>> getAllUsers() {
        final List<User> allUsersList = userRepository.findAll();
        return ResponseEntity.ok(allUsersList);
    }

    @PostMapping(path = "/users")
    public ResponseEntity<Void> createUser(@Valid @RequestBody final User user) {
        final String userEmail = user.getEmail();
        final String accountName = user.getAccountName();
        final Optional<User> alreadyFoundEmail = userRepository.findUserByEmail(userEmail);
        final Optional<User> alreadyFoundAccountName = userRepository.findUserByAccountName(accountName);
        assertBodyParamIsUnique(userEmail, alreadyFoundEmail);
        assertBodyParamIsUnique(accountName, alreadyFoundAccountName);
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") final Long userId) {
        assertUserExists(userId);
        userRepository.deleteById(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "/users/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable("id") final Long userId,
                                           @RequestBody @NotNull final User updatedUser) {
        final Optional<User> userToUpdateOptional = userRepository.findById(userId);
        userToUpdateOptional.ifPresent(userToUpdate -> {
            setUserFields(userToUpdate, updatedUser);
            userRepository.save(userToUpdate);
        });
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void assertBodyParamIsUnique(final String bodyParam, final Optional<User> alreadyFoundBodyParam) {
        if (alreadyFoundBodyParam.isPresent()) {
            final String message = String.format("%s already exist from another user account", bodyParam);
            throw new IllegalStateException(message);
        }
    }

    private void assertUserExists(final Long id) {
        final boolean doesIdExist = userRepository.existsById(id);
        if (!doesIdExist) {
            final String message = String.format("%s doesn't exist", id);
            throw new IllegalStateException(message);
        }
    }

    private void setUserFields(final User userToUpdate, final User updatedUser) {
        userToUpdate.setEmail(updatedUser.getEmail());
        userToUpdate.setPassword(updatedUser.getPassword());
        userToUpdate.setAccountName(updatedUser.getAccountName());
        userToUpdate.setFirstName(updatedUser.getFirstName());
        userToUpdate.setLastName(updatedUser.getLastName());
    }
}
