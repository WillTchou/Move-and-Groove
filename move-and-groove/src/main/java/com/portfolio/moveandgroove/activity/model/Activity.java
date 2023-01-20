/*package com.portfolio.moveandgroove.activity.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

import com.portfolio.moveandgroove.user.model.User;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ACTIVITY")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME", length = 150, nullable = false)
    @NotBlank(message = "Name is mandatory")
    private String name;
    @Column(name = "CREATION_DATA", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate creationDate;
    @Column(name = "DURATION")
    @Temporal(TemporalType.TIME)
    private LocalTime duration;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    public Activity(final Long id,
                    final String name,
                    final LocalDate creationDate,
                    final LocalTime duration,
                    final User user) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.duration = duration;
        this.user = user;
    }

    public Activity(final String name,
                    final LocalDate creationDate,
                    final LocalTime duration,
                    final User user) {
        this.name = name;
        this.creationDate = creationDate;
        this.duration = duration;
        this.user = user;
    }
}*/
