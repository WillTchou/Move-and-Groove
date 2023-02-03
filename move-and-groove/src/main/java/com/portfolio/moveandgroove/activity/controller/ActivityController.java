package com.portfolio.moveandgroove.activity.controller;

import com.portfolio.moveandgroove.activity.model.ActivityDTO;
import com.portfolio.moveandgroove.activity.model.ActivityRequest;
import com.portfolio.moveandgroove.activity.service.ActivityService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.Set;

@RestController
@RequestMapping(path = "api/v1/activities")
public class ActivityController {

    private final ActivityService activityService;

    @Autowired
    public ActivityController(final ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping(path = "/")
    public ResponseEntity<Set<ActivityDTO>> getAllActivities(@RequestHeader(name = "userId") final Long userId) {
        return ResponseEntity.ok(activityService.getAllActivities(userId));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ActivityDTO> getActivityById(@RequestHeader(name = "userId") final Long userId,
                                                       @PathVariable("id") final Long activityId) {
        return ResponseEntity.ok(activityService.getActivityById(userId, activityId));
    }

    @Transactional
    @PostMapping(path = "/")
    public ResponseEntity<Void> createActivity(@RequestBody @NotNull final ActivityRequest activityRequest,
                                               @RequestHeader(name = "userId") final Long userId) {
        final String activityRequestName = activityRequest.getName();
        final LocalTime activityRequestNameDuration = LocalTime.parse(activityRequest.getDuration());
        activityService.createActivity(activityRequestName, activityRequestNameDuration, userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Transactional
    @PutMapping(path = "/{id}")
    public ResponseEntity<Void> updateActivity(@PathVariable("id") final Long activityId,
                                               @RequestBody @NotNull final ActivityRequest activityRequest,
                                               @RequestHeader(name = "userId") final Long userId) {
        final String activityRequestName = activityRequest.getName();
        final LocalTime activityRequestNameDuration = LocalTime.parse(activityRequest.getDuration());
        activityService.updateActivity(userId, activityId, activityRequestName, activityRequestNameDuration);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteActivity(@RequestHeader(name = "userId") final Long userId,
                                               @PathVariable("id") final Long activityId) {
        activityService.deleteActivity(userId, activityId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
