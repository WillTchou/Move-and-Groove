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

    public Set<ActivityDTO> getAllActivities(final Long userId) {
        return activityRepository.findActivitiesForUser(userId)
                .stream()
                .map(activityDTOMapper)
                .collect(Collectors.toSet());
    }

    public ActivityDTO getActivityById(final Long userId, final Long activityId) {
        final String message = String.format("Activity or User with id: %s or %s doesn't exist", activityId, userId);
        return activityRepository.findActivityForUserById(userId, activityId)
                .map(activityDTOMapper)
                .orElseThrow(() -> new IllegalStateException(message));
    }

    public void createActivity(final String name, final LocalTime duration, final Long userId) {
        final String message = String.format("User %s doesn't exist", userId);
        final User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new IllegalStateException(message));
        final Activity activity = new Activity(name, duration, user);
        activityRepository.save(activity);
    }

    public void updateActivity(final Long userId, final Long activityId, final String name, final LocalTime duration) {
        activityRepository.findActivityForUserById(userId, activityId)
                .ifPresent(activityToUpdate -> {
                    setActivityFields(activityToUpdate, name, duration);
                    activityRepository.save(activityToUpdate);
                });
    }

    public void deleteActivity(final Long userId, final Long activityId) {
        assertUserOwnerActivity(userId, activityId);
        activityRepository.deleteById(activityId);
    }

    private void assertUserOwnerActivity(Long userId, Long activityId) {
        if (!isUserActivityOwner(userId, activityId)) {
            final String message = String.format("User %s doesn't own this activity", userId);
            throw new IllegalArgumentException(message);
        }
    }

    private boolean isUserActivityOwner(final Long userId, final Long activityId) {
        return activityRepository.findActivityForUserById(userId, activityId).isPresent();
    }

    private void setActivityFields(final Activity activityToUpdate, final String name, final LocalTime duration) {
        activityToUpdate.setName(name);
        activityToUpdate.setDuration(duration);
    }
}
