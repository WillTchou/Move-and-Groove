package com.portfolio.moveandgroove.user.controller;

import com.portfolio.moveandgroove.user.model.User;
import com.portfolio.moveandgroove.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/users/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable("id") final Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping(path = "/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") final Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional
    @PutMapping(path = "/users/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable("id") final Long userId,
                                           @RequestBody @NotNull final User updatedUser) {
        userService.updateUser(userId, updatedUser);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
