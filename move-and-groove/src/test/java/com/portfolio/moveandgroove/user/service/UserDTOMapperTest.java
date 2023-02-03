package com.portfolio.moveandgroove.user.service;

import com.portfolio.moveandgroove.activity.model.Activity;
import com.portfolio.moveandgroove.user.model.Role;
import com.portfolio.moveandgroove.user.model.User;
import com.portfolio.moveandgroove.user.model.UserDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class UserDTOMapperTest {
    public static final long ID = 1L;
    public static final String FIRSTNAME = "Ichigo";
    public static final String LASTNAME = "Kurosaki";
    public static final String USERNAME = "zangetsu";
    public static final String EMAIL = "kurosaki15@bankai.jp";
    public static final Role ROLE = Role.USER;
    public static final Activity ACTIVITY = new Activity("escrime", LocalTime.NOON, new User());
    private UserDTOMapper userDTOMapper;

    @BeforeEach
    void setup() {
        userDTOMapper = new UserDTOMapper();
    }

    @Test
    void apply() {
        //Given
        final User user = buildUser();
        final UserDTO userDTO = new UserDTO(ID, FIRSTNAME, LASTNAME, USERNAME, EMAIL, ROLE, Set.of(ACTIVITY));
        //When
        final UserDTO result = userDTOMapper.apply(user);
        //Then
        Assertions.assertThat(result)
                .isNotNull()
                .isEqualTo(userDTO);
    }

    private User buildUser() {
        return User.builder()
                .id(ID)
                .firstname(FIRSTNAME)
                .lastname(LASTNAME)
                .username(USERNAME)
                .password("oriheme")
                .email(EMAIL)
                .role(ROLE)
                .activities(Set.of(ACTIVITY))
                .build();
    }
}
