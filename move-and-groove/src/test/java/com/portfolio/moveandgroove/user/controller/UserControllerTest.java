package com.portfolio.moveandgroove.user.controller;

import com.portfolio.moveandgroove.user.model.User;
import com.portfolio.moveandgroove.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private static final String FIRST_NAME = "Son";
    private static final String LAST_NAME = "Goku";
    private static final String EMAIL = "songoku@gmail.com";
    private static final String PASSWORD = "chichi";

    private UserController userController;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        Assertions.assertNotNull(userRepository);
        userController = new UserController(userRepository);
    }

    @Test
    void getAllUsers() {
        // Given
        final User user = buildUser();
        final List<User> usersList = Collections.singletonList(user);
        // When
        Mockito.when(userRepository.findAll()).thenReturn(usersList);
        final ResponseEntity<List<User>> result = userController.getAllUsers();
        // Then
        Assertions.assertNotNull(result);
        final List<User> resultBody = result.getBody();
        Assertions.assertNotNull(resultBody);
        Assertions.assertFalse(resultBody.isEmpty());
        Assertions.assertEquals(resultBody.get(0), user);
        Mockito.verify(userRepository).findAll();
    }

    @Test
    void getUserByIdThatDoesNotExist() {
        // Given
        final User user = buildUserWithNull();
        // When && Then
        final Long userId= user.getId();
        Assertions.assertThrows(IllegalStateException.class, () -> userController.getUserById(userId));
    }

    @Test
    void getUserById() {
        // Given
        final User user = buildUser();
        final Long userId = user.getId();
        final Optional<User> usersList = Optional.of(user);
        // When
        Mockito.when(userRepository.findById(userId)).thenReturn(usersList);
        final ResponseEntity<Optional<User>> result = userController.getUserById(userId);
        // Then
        Assertions.assertNotNull(result);
        final Optional<User> resultBody = result.getBody();
        Assertions.assertNotNull(resultBody);
        Assertions.assertTrue(resultBody.isPresent());
        Assertions.assertEquals(resultBody.get(), user);
        Mockito.verify(userRepository).findById(userId);
    }

    @Test
    void deleteUserByIdThatDoesNotExist() {
        // Given
        final User user = buildUserWithNull();
        // When & Then
        final Long userId= user.getId();
        Assertions.assertThrows(IllegalStateException.class, () -> userController.deleteUser(userId));
    }

    @Test
    void deleteUser() {
        // Given
        final Long userId = 1L;
        // When
        Mockito.when(userRepository.existsById(userId)).thenReturn(true);
        userController.deleteUser(userId);
        // Then
        Mockito.verify(userRepository).deleteById(userId);
    }

    @Test
    void createUserWithNotUniqueEmail() {
        // Given
        final User user = buildUser();
        final String userEmail = user.getEmail();
        // When
        Mockito.when(userRepository.findUserByEmail(userEmail)).thenReturn(Optional.of(
                buildUser("blabladodo", userEmail)));
        // Then
        Assertions.assertThrows(IllegalStateException.class, () -> userController.createUser(user));
    }

    @Test
    void createUserWithNotUniqueAccountName() {
        // Given
        final User user = buildUser();
        final String userAccountName = user.getAccountName();
        // When
        Mockito.when(userRepository.findUserByAccountName(userAccountName)).thenReturn(Optional.of(
                buildUser(userAccountName, "blabla@dodo.com")));
        // Then
        Assertions.assertThrows(IllegalStateException.class, () -> userController.createUser(user));
    }

    @Test
    void createUser() {
        // Given
        final User user = buildUser();
        final String userEmail = user.getEmail();
        final String userAccountName = user.getAccountName();
        // When
        Mockito.when(userRepository.findUserByEmail(userEmail)).thenReturn(Optional.empty());
        Mockito.when(userRepository.findUserByAccountName(userAccountName)).thenReturn(Optional.empty());
        userController.createUser(user);
        // Then
        Mockito.verify(userRepository).save(user);
    }

    @Test
    void updateUser() {
        // Given
        final User user1 = buildUser();
        final User user2 = new User("Naruto", "Uzumaki", "hinata", "hokage", "hokage@gmail.com");
        final Long user1Id = user1.getId();
        // When
        Mockito.when(userRepository.findById(user1Id)).thenReturn(Optional.of(user1));
        userController.updateUser(user1Id, user2);
        // Then
        Assertions.assertNotNull(user1);
        Assertions.assertEquals(user1.getFirstName(), user2.getFirstName());
        Assertions.assertEquals(user1.getLastName(), user2.getLastName());
        Assertions.assertEquals(user1.getAccountName(), user2.getAccountName());
        Assertions.assertEquals(user1.getPassword(), user2.getPassword());
        Assertions.assertEquals(user1.getEmail(), user2.getEmail());
        Mockito.verify(userRepository).findById(user1Id);
    }

    private User buildUser() {
        return new User(1L, FIRST_NAME, LAST_NAME, "songoku",
                PASSWORD, EMAIL);
    }

    private User buildUser(final String accountName, final String email) {
        return new User(1L, FIRST_NAME, LAST_NAME, accountName,
                PASSWORD, email);
    }

    private User buildUserWithNull() {
        return new User(2L, FIRST_NAME, "Gohan", "songohan",
                "pan", EMAIL);
    }
}
