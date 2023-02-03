package com.portfolio.moveandgroove.activity.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

import com.portfolio.moveandgroove.user.model.User;

@Data
@NoArgsConstructor
@Entity
@Table(name = "activities")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", length = 150, nullable = false)
    @NotBlank(message = "Name is mandatory")
    private String name;
    @Column(name = "creation_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    @Temporal(TemporalType.DATE)
    private LocalDate creationDate;
    @Column(name = "duration")
    @Temporal(TemporalType.TIME)
    private LocalTime duration;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Activity(final Long id,
                    final String name,
                    final LocalTime duration,
                    final User user) {
        this.id = id;
        this.name = name;
        this.creationDate = LocalDate.now();
        this.duration = duration;
        this.user = user;
    }
    public Activity(final String name,
                    final LocalTime duration,
                    final User user) {
        this.name = name;
        this.creationDate = LocalDate.now();
        this.duration = duration;
        this.user = user;
    }
}
