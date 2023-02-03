package com.portfolio.moveandgroove.user.service;

import com.portfolio.moveandgroove.user.model.User;
import com.portfolio.moveandgroove.user.model.UserDTO;
import com.portfolio.moveandgroove.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private static final Long ID = 1L;
    private static final String FIRST_NAME = "Son";
    private static final String LAST_NAME = "Goku";
    private static final String EMAIL = "songoku@gmail.com";
    private static final String PASSWORD = "chichi";

    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    private final UserDTOMapper userDTOMapper = new UserDTOMapper();

    @BeforeEach
    void setup() {
        Assertions.assertNotNull(userRepository);
        Assertions.assertNotNull(userDTOMapper);
        Assertions.assertNotNull(passwordEncoder);
        userService = new UserService(userRepository, userDTOMapper, passwordEncoder);
    }

    @Test
    void getAllUsers() {
        // Given
        final User user = buildUser();
        final List<User> usersList = Collections.singletonList(user);
        // When
        Mockito.when(userRepository.findAll()).thenReturn(usersList);
        final List<UserDTO> result = userService.getAllUsers();
        // Then
        final UserDTO userDTO = userDTOMapper.apply(user);
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(result.get(0), userDTO);
        Mockito.verify(userRepository).findAll();
    }

    @Test
    void getUserByIdThatDoesNotExist() {
        // Given
        final User user = buildUserWithNull();
        // When && Then
        final Long userId = user.getId();
        Assertions.assertThrows(IllegalStateException.class, () -> userService.getUserById(userId));
    }

    @Test
    void getUserById() {
        // Given
        final User user = buildUser();
        final Long userId = user.getId();
        final Optional<User> optionalUser = Optional.of(user);
        final UserDTO userDTO = userDTOMapper.apply(user);
        // When
        Mockito.when(userRepository.findById(userId)).thenReturn(optionalUser);
        final UserDTO result = userService.getUserById(userId);
        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result, userDTO);
        Mockito.verify(userRepository).findById(userId);
    }

    @Test
    void deleteUserByIdThatDoesNotExist() {
        // Given
        final User user = buildUserWithNull();
        // When & Then
        final Long userId = user.getId();
        Assertions.assertThrows(IllegalStateException.class, () -> userService.deleteUser(userId));
    }

    @Test
    void deleteUser() {
        // Given & When
        Mockito.when(userRepository.existsById(ID)).thenReturn(true);
        userService.deleteUser(ID);
        // Then
        Mockito.verify(userRepository).deleteById(ID);
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
        Assertions.assertThrows(IllegalStateException.class, () -> userService.createUser(user));
    }

    @Test
    void createUserWithNotUniqueUserName() {
        // Given
        final User user = buildUser();
        final String username = user.getUsername();
        // When
        Mockito.when(userRepository.findUserByUsername(username)).thenReturn(Optional.of(
                buildUser(username, "blabla@dodo.com")));
        // Then
        Assertions.assertThrows(IllegalStateException.class, () -> userService.createUser(user));
    }

    @Test
    void createUser() {
        // Given
        final User user = buildUser();
        final String userEmail = user.getEmail();
        final String userAccountName = user.getUsername();
        // When
        Mockito.when(userRepository.findUserByEmail(userEmail)).thenReturn(Optional.empty());
        Mockito.when(userRepository.findUserByUsername(userAccountName)).thenReturn(Optional.empty());
        userService.createUser(user);
        // Then
        Mockito.verify(userRepository).save(user);
    }

    @Test
    void updateUser() {
        // Given
        final User user1 = buildUser();
        final User user2 = User.builder()
                .firstname("Naruto")
                .lastname("Uzumaki")
                .username("hinata")
                .password(passwordEncoder.encode("hokage"))
                .email("hokage@gmail.com")
                .build();
        final Long user1Id = user1.getId();
        // When
        Mockito.when(userRepository.findById(user1Id)).thenReturn(Optional.of(user1));
        userService.updateUser(user1Id, user2);
        // Then
        Assertions.assertNotNull(user1);
        Assertions.assertEquals(user1.getFirstname(), user2.getFirstname());
        Assertions.assertEquals(user1.getLastname(), user2.getLastname());
        Assertions.assertEquals(user1.getUsername(), user2.getUsername());
        Assertions.assertEquals(user1.getPassword(), user2.getPassword());
        Assertions.assertEquals(user1.getEmail(), user2.getEmail());
        Mockito.verify(userRepository).findById(user1Id);
    }

    private User buildUser() {
        return User.builder()
                .id(ID)
                .firstname(FIRST_NAME)
                .lastname(LAST_NAME)
                .username("songoku")
                .password(passwordEncoder.encode(PASSWORD))
                .email(EMAIL)
                .build();
    }

    private User buildUser(final String userName, final String email) {
        return User.builder()
                .id(ID)
                .firstname(FIRST_NAME)
                .lastname(LAST_NAME)
                .username(userName)
                .password(PASSWORD)
                .email(email)
                .build();
    }

    private User buildUserWithNull() {
        return User.builder()
                .id(2L)
                .firstname(FIRST_NAME)
                .lastname("Gohan")
                .username("songohan")
                .password("pan")
                .email(EMAIL)
                .build();
    }
}
