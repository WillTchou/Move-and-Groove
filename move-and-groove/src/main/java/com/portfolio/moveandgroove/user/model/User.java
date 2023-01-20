package com.portfolio.moveandgroove.user.model;

//import com.portfolio.moveandgroove.activity.model.Activity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;
    @Column(name = "firstname", length = 50, nullable = false)
    @NotBlank(message = "Firstname is mandatory")
    private String firstname;
    @Column(name = "lastname", length = 50, nullable = false)
    @NotBlank(message = "Lastname is mandatory")
    private String lastname;
    @Column(name = "username", length = 50, nullable = false, unique = true)
    @Pattern(regexp = "^([a-zA-Z0-9.]){0,1}([a-zA-Z0-9.])+$")
    @NotBlank(message = "Username is mandatory")
    private String username;
    @Column(name = "email", length = 250, nullable = false, unique = true)
    @Email
    @NotBlank(message = "Email is mandatory")
    private String email;
    @Column(name = "role", length = 250)
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "password", length = 250, nullable = false)
    @NotNull
    private String password;

    /*@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Activity> activities;*/

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
