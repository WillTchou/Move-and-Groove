package com.portfolio.moveandgroove.user.model;

//import com.portfolio.moveandgroove.activity.model.Activity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "FIRST_NAME", length = 50, nullable = false)
    @NotBlank(message = "FirstName is mandatory")
    private String firstName;
    @Column(name = "LAST_NAME", length = 50, nullable = false)
    @NotBlank(message = "LastName is mandatory")
    private String lastName;
    @Column(name = "ACCOUNT_NAME", length = 50, nullable = false, unique = true)
    @NotBlank(message = "AccountName is mandatory")
    private String accountName;
    @Column(name = "PASSWORD", length = 150, nullable = false)
    @NotBlank(message = "Password is mandatory")
    private String password;
    @Column(name = "EMAIL_ADDRESS", length = 250, nullable = false, unique = true)
    @Email
    @NotBlank(message = "Email is mandatory")
    private String email;

    /*@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Activity> activities;*/

    public User(final Long id,
                final String firstName,
                final String lastName,
                final String accountName,
                final String password,
                final String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountName = accountName;
        this.password = password;
        this.email = email;
    }

    public User(final String firstName,
                final String lastName,
                final String accountName,
                final String password,
                final String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountName = accountName;
        this.password = password;
        this.email = email;
    }
}
