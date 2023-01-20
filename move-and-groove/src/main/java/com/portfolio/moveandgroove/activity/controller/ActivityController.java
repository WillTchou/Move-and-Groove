/*package com.portfolio.moveandgroove.activity.controller;

import com.portfolio.moveandgroove.activity.model.Activity;
import com.portfolio.moveandgroove.activity.repository.ActivityRepository;
import com.portfolio.moveandgroove.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Controller
@RequestMapping(path = "api/v1/activity")
public class ActivityController {

    @Autowired
    private final ActivityRepository activityRepository;

    @Autowired
    private final User user;

    public ActivityController(final ActivityRepository activityRepository, final User user) {
        this.activityRepository = activityRepository;
        this.user = user;
    }

    @GetMapping(path = "/activities")
    public ResponseEntity<Optional<Activity>> getAllActivities() {
        final Long userId = user.getId();
        final Optional<Activity> allActivitiesForUser = activityRepository.findActivitiesForUser(userId);
        return ResponseEntity.ok(allActivitiesForUser);
    }

    @GetMapping(path = "/activities/{id}")
    public ResponseEntity<Optional<Activity>> getActivityById(@PathVariable("id") final Long activityId) {
        final Long userId = user.getId();
        final String message = String.format("%s doesn't exist", activityId);
        final Optional<Activity> foundActivityById = Optional.ofNullable(
                activityRepository.findActivityForUserById(userId, activityId)
                        .orElseThrow(() -> new IllegalStateException(message)));
        return ResponseEntity.ok(foundActivityById);
    }

    @PostMapping(path = "/activities")
    public ResponseEntity<Void> createActivity(@RequestParam final String name,
                                               @RequestParam final LocalTime duration) {
        final Activity activity = new Activity(name, LocalDate.now(), duration, user);
        activityRepository.save(activity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/activities/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable("id") final Long activityId) {
        assertActivityExists(activityId);
        activityRepository.deleteById(activityId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void assertActivityExists(final Long id) {
        final boolean doesIdExist = activityRepository.existsById(id);
        if (!doesIdExist) {
            final String message = String.format("%s doesn't exist", id);
            throw new IllegalStateException(message);
        }
    }
}*/
