package com.portfolio.moveandgroove.user.service;

import com.portfolio.moveandgroove.activity.model.Activity;
import com.portfolio.moveandgroove.user.model.Role;
import com.portfolio.moveandgroove.user.model.User;
import com.portfolio.moveandgroove.user.model.UserDTO;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.function.Function;

@Service
public class UserDTOMapper implements Function<User, UserDTO> {
    @Override
    public UserDTO apply(final User user) {
        final Long id = user.getId();
        final String firstname = user.getFirstname();
        final String lastname = user.getLastname();
        final String username = user.getUsername();
        final String email = user.getEmail();
        final Role role = user.getRole();
        final Set<Activity> activities = user.getActivities();
        return new UserDTO(id, firstname, lastname, username, email, role, activities);
    }
}
