package com.portfolio.moveandgroove.activity.service;

import com.portfolio.moveandgroove.activity.model.Activity;
import com.portfolio.moveandgroove.activity.model.ActivityDTO;
import com.portfolio.moveandgroove.activity.repository.ActivityRepository;
import com.portfolio.moveandgroove.user.model.User;
import com.portfolio.moveandgroove.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityDTOMapper activityDTOMapper;
    private final UserRepository userRepository;

    @Autowired
    public ActivityService(final ActivityRepository activityRepository,
                           final ActivityDTOMapper activityDTOMapper,
                           final UserRepository userRepository) {
        this.activityRepository = activityRepository;
        this.activityDTOMapper = activityDTOMapper;
        this.userRepository = userRepository;
    }

    public Set<ActivityDTO> getAllActivities(final String username) {
        return activityRepository.findActivitiesForUser(username)
                .stream()
                .map(activityDTOMapper)
                .collect(Collectors.toSet());
    }

    public ActivityDTO getActivityById(final String username, final Long activityId) {
        final String message = String.format("Activity or User with id: %s or %s doesn't exist", activityId, username);
        return activityRepository.findActivityForUserById(username, activityId)
                .map(activityDTOMapper)
                .orElseThrow(() -> new IllegalStateException(message));
    }

    public void createActivity(final String name, final LocalTime duration, final String username) {
        final String message = String.format("User %s doesn't exist", username);
        final User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new IllegalStateException(message));
        final Activity activity = new Activity(name, duration, user);
        activityRepository.save(activity);
    }

    public void updateActivity(final String username, final Long activityId, final String name, final LocalTime duration) {
        activityRepository.findActivityForUserById(username, activityId)
                .ifPresent(activityToUpdate -> {
                    setActivityFields(activityToUpdate, name, duration);
                    activityRepository.save(activityToUpdate);
                });
    }

    public void deleteActivity(final String username, final Long activityId) {
        assertUserOwnerActivity(username, activityId);
        activityRepository.deleteById(activityId);
    }

    private void assertUserOwnerActivity(final String username,final Long activityId) {
        if (!isUserActivityOwner(username, activityId)) {
            final String message = String.format("User %s doesn't own this activity", username);
            throw new IllegalArgumentException(message);
        }
    }

    private boolean isUserActivityOwner(final String username, final Long activityId) {
        return activityRepository.findActivityForUserById(username, activityId).isPresent();
    }

    private void setActivityFields(final Activity activityToUpdate, final String name, final LocalTime duration) {
        activityToUpdate.setName(name);
        activityToUpdate.setDuration(duration);
    }
}
