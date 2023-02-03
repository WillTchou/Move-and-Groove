package com.portfolio.moveandgroove.user.controller;

import com.portfolio.moveandgroove.activity.model.Activity;
import com.portfolio.moveandgroove.user.model.Role;
import com.portfolio.moveandgroove.user.model.User;
import com.portfolio.moveandgroove.user.model.UserDTO;
import com.portfolio.moveandgroove.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    public static final long ID = 1L;
    public static final String FIRSTNAME = "Ichigo";
    public static final String LASTNAME = "Kurosaki";
    public static final String USERNAME = "zangetsu";
    public static final String EMAIL = "kurosaki15@bankai.jp";
    public static final Role ROLE = Role.USER;
    public static final Activity ACTIVITY = new Activity("escrime", LocalTime.NOON, new User());

    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setup() {
        Assertions.assertThat(userService).isNotNull();
        userController = new UserController(userService);
    }

    @Test
    void getUserById() {
        //Given
        final UserDTO userDTO = buildUserDTO();
        //When
        Mockito.when(userService.getUserById(ID)).thenReturn(userDTO);
        final ResponseEntity<UserDTO> result = userController.getUserById(ID);
        //Then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        final UserDTO finalUserDTO = result.getBody();
        Assertions.assertThat(finalUserDTO)
                .isNotNull()
                .isEqualTo(userDTO);
    }

    @Test
    void getAllUsers() {
        //Given
        final UserDTO userDTO = buildUserDTO();
        //When
        Mockito.when(userService.getAllUsers()).thenReturn(List.of(userDTO));
        final ResponseEntity<List<UserDTO>> result = userController.getAllUsers();
        //Then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        final List<UserDTO> listFinalUserDTO = result.getBody();
        Assertions.assertThat(listFinalUserDTO)
                .isNotEmpty()
                .hasSize(1);
        final UserDTO finalUserDTO = listFinalUserDTO.get(0);
        Assertions.assertThat(finalUserDTO).isEqualTo(userDTO);
    }

    @Test
    void deleteUser() {
        //Given & When
        final ResponseEntity<Void> result = userController.deleteUser(ID);
        //Then
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Mockito.verify(userService, Mockito.times(1)).deleteUser(ID);
    }

    @Test
    void updateUser() {
        //Given
        final User user = buildUser();
        //When
        final ResponseEntity<Void> result = userController.updateUser(ID, user);
        //Then
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Mockito.verify(userService).updateUser(ID, user);
    }

    private UserDTO buildUserDTO() {
        return new UserDTO(ID, FIRSTNAME, LASTNAME, USERNAME, EMAIL, ROLE, Set.of(ACTIVITY));
    }

    private User buildUser() {
        return User.builder()
                .id(ID)
                .firstname(FIRSTNAME)
                .lastname(LASTNAME)
                .username(USERNAME)
                .email(EMAIL)
                .password("oriheme")
                .role(ROLE)
                .activities(Set.of(ACTIVITY))
                .build();
    }
}
