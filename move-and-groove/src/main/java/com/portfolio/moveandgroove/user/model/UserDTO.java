package com.portfolio.moveandgroove.user.model;

import com.portfolio.moveandgroove.activity.model.Activity;

import java.util.Set;

public record UserDTO(Long id,
                      String firstname,
                      String lastname,
                      String username,
                      String email,
                      Role role,
                      Set<Activity> activities) {}
