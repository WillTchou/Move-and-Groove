package com.portfolio.moveandgroove.Activity.service;

import com.portfolio.moveandgroove.activity.model.Activity;
import com.portfolio.moveandgroove.activity.model.ActivityDTO;
import com.portfolio.moveandgroove.activity.repository.ActivityRepository;
import com.portfolio.moveandgroove.activity.service.ActivityDTOMapper;
import com.portfolio.moveandgroove.activity.service.ActivityService;
import com.portfolio.moveandgroove.user.model.Role;
import com.portfolio.moveandgroove.user.model.User;
import com.portfolio.moveandgroove.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class ActivityServiceTest {
    private static final Long ID = 1L;
    private static final String NAME = "Boxing";
    private static final LocalTime DURATION = LocalTime.NOON;
    private ActivityService activityService;

    @Mock
    private ActivityRepository activityRepository;
    @Mock
    private UserRepository userRepository;
    private final ActivityDTOMapper activityDTOMapper = new ActivityDTOMapper();

    @BeforeEach
    void setup() {
        Assertions.assertThat(activityRepository).isNotNull();
        Assertions.assertThat(activityDTOMapper).isNotNull();
        Assertions.assertThat(userRepository).isNotNull();
        activityService = new ActivityService(activityRepository, activityDTOMapper, userRepository);
    }

    @Test
    void getAllActivities() {
        //Given
        final Activity activity = buildActivity();
        final String username = activity.getUser().getUsername();
        final Set<Activity> activitySet = Set.of(activity);
        //When
        Mockito.when(activityRepository.findActivitiesForUser(username)).thenReturn(activitySet);
        final Set<ActivityDTO> result = activityService.getAllActivities(username);
        //Then
        final ActivityDTO activityDTO = activityDTOMapper.apply(activity);
        Assertions.assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(result.iterator().next()).isEqualTo(activityDTO);
        Mockito.verify(activityRepository).findActivitiesForUser(username);
    }

    @Test
    void getActivityByIdThatDoesNotExist() {
        // Given
        final Activity activity = buildActivityWithNull();
        final String username = activity.getUser().getUsername();
        // When && Then
        final Long activityId = activity.getId();
        Assertions.assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> activityService.getActivityById(username, activityId))
                .withMessage("Activity or User with id: %s or %s doesn't exist", activityId, username);
    }

    @Test
    void getActivityById() {
        //Given
        final Activity activity = buildActivity();
        final Long activityId = activity.getId();
        final String username = buildUser().getUsername();
        final Optional<Activity> optionalActivity = Optional.of(activity);
        final ActivityDTO activityDTO = activityDTOMapper.apply(activity);
        //When
        Mockito.when(activityRepository.findActivityForUserById(username, activityId)).thenReturn(optionalActivity);
        final ActivityDTO result = activityService.getActivityById(username, activityId);
        //Then
        Assertions.assertThat(result)
                .isNotNull()
                .isEqualTo(activityDTO);
        Mockito.verify(activityRepository).findActivityForUserById(username, activityId);
    }

    @Test
    void deleteActivityWhenIsUserOwnerActivity() {
        // Given
        final String username = buildUser().getUsername();
        // When && Then
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> activityService.deleteActivity(username, ID))
                .withMessage("User %s doesn't own this activity", username);
    }

    @Test
    void deleteActivity() {
        // Given
        final String username = buildUser().getUsername();
        final Activity activity = buildActivity();
        final Optional<Activity> optionalActivity = Optional.of(activity);
        // When
        Mockito.when(activityRepository.findActivityForUserById(username, ID)).thenReturn(optionalActivity);
        activityService.deleteActivity(username, ID);
        // Then
        Mockito.verify(activityRepository, Mockito.times(1)).findActivityForUserById(username, ID);
        Mockito.verify(activityRepository, Mockito.times(1)).deleteById(ID);
    }

    @Test
    void createActivityWithUnknownUser() {
        // Given
        final String username = "blabla";
        // When && Then
        Assertions.assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> activityService.createActivity("baseball", DURATION, username))
                .withMessage("User %s doesn't exist", username);
    }

    @Test
    void createActivity() {
        //Given
        final String username = buildUser().getUsername();
        final String activityName = "tennis";
        //When
        Mockito.when(userRepository.findUserByUsername(username)).thenReturn(Optional.of(buildUser()));
        activityService.createActivity(activityName, DURATION, username);
        //Then
        final Activity activity = new Activity(activityName, DURATION, buildUser());
        Mockito.verify(activityRepository).save(activity);
    }

    @Test
    void updateActivity() {
        //Given
        final Activity activity1 = buildActivity();
        final Activity activity2 = new Activity(2L, "Karaté", LocalTime.of(0, 3), buildUser());
        final String username = buildUser().getUsername();
        final Long activity1Id = activity1.getId();
        //When
        Mockito.when(activityRepository.findActivityForUserById(username, activity1Id)).thenReturn(Optional.of(activity1));
        activityService.updateActivity(username, activity1Id, activity2.getName(), activity2.getDuration());
        //Then
        Assertions.assertThat(activity1.getName()).isEqualTo(activity2.getName());
        Assertions.assertThat(activity1.getDuration()).isEqualTo(activity2.getDuration());
        Mockito.verify(activityRepository).findActivityForUserById(username, activity1Id);
    }

    private static User buildUser() {
        return User.builder()
                .id(2L)
                .firstname("Ippo")
                .lastname("Makunouichi")
                .username("godOfWind")
                .email("godOfWind@kamogawa.jp")
                .role(Role.USER)
                .password("kumi")
                .build();
    }

    private Activity buildActivity() {
        return new Activity(ID, NAME, DURATION, buildUser());
    }

    private Activity buildActivityWithNull() {
        return new Activity(null, NAME, DURATION, buildUser());
    }
}
