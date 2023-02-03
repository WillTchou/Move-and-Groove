package com.portfolio.moveandgroove.user.controller;

import com.portfolio.moveandgroove.user.model.User;
import com.portfolio.moveandgroove.user.model.UserDTO;
import com.portfolio.moveandgroove.user.service.UserService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") final Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") final Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional
    @PutMapping(path = "/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable("id") final Long userId,
                                           @RequestBody @NotNull final User updatedUser) {
        userService.updateUser(userId, updatedUser);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
