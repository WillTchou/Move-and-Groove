package com.portfolio.moveandgroove.Activity.controller;

import com.portfolio.moveandgroove.activity.controller.ActivityController;
import com.portfolio.moveandgroove.activity.model.ActivityDTO;
import com.portfolio.moveandgroove.activity.model.ActivityRequest;
import com.portfolio.moveandgroove.activity.service.ActivityService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class ActivityControllerTest {

    private static final String USERNAME = "Isagi";

    private static final Long ACTIVITY_ID = 2L;
    private static final String ACTIVITY_NAME = "Soccer";
    private static final LocalTime ACTIVITY_DURATION = LocalTime.of(1, 30);

    private ActivityController activityController;
    @Mock
    private ActivityService activityService;

    @BeforeEach
    void setup() {
        Assertions.assertThat(activityService).isNotNull();
        activityController = new ActivityController(activityService);
    }

    @Test
    void getAllActivities() {
        //Given
        final Set<ActivityDTO> activities = Set.of(buildActivity());
        //When
        Mockito.when(activityService.getAllActivities(USERNAME)).thenReturn(activities);
        final ResponseEntity<Set<ActivityDTO>> result = activityController.getAllActivities(USERNAME);
        //Then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        final Set<ActivityDTO> finalResult = result.getBody();
        Assertions.assertThat(finalResult)
                .isNotNull()
                .isEqualTo(activities);
    }

    @Test
    void getActivityById() {
        //Given
        final ActivityDTO activities = buildActivity();
        //When
        Mockito.when(activityService.getActivityById(USERNAME, ACTIVITY_ID)).thenReturn(activities);
        final ResponseEntity<ActivityDTO> result = activityController.getActivityById(USERNAME, ACTIVITY_ID);
        //Then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        final ActivityDTO finalResult = result.getBody();
        Assertions.assertThat(finalResult)
                .isNotNull()
                .isEqualTo(activities);
    }

    @Test
    void createActivity() {
        //Given
        final ActivityRequest activityRequest = ActivityRequest.builder()
                .name(ACTIVITY_NAME)
                .duration(ACTIVITY_DURATION.toString())
                .build();
        final String activityRequestName = activityRequest.getName();
        final LocalTime activityRequestNameDuration = LocalTime.parse(activityRequest.getDuration());
        //When
        final ResponseEntity<Void> result = activityController.createActivity(activityRequest, USERNAME);
        //Then
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Mockito.verify(activityService, Mockito.times(1))
                .createActivity(activityRequestName, activityRequestNameDuration, USERNAME);
    }

    @Test
    void updateActivity() {
        // Given
        final ActivityRequest activityRequest = ActivityRequest.builder()
                .name(ACTIVITY_NAME)
                .duration(ACTIVITY_DURATION.toString())
                .build();
        final LocalTime activityRequestNameDuration = LocalTime.parse(activityRequest.getDuration());
        final String activityRequestName = activityRequest.getName();
        // When
        final ResponseEntity<Void> result = activityController.updateActivity(ACTIVITY_ID, activityRequest, USERNAME);
        // Then
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Mockito.verify(activityService).updateActivity(USERNAME, ACTIVITY_ID, activityRequestName, activityRequestNameDuration);
    }

    @Test
    void deleteActivity() {
        //Given & When
        final ResponseEntity<Void> result = activityController.deleteActivity(USERNAME, ACTIVITY_ID);
        //Then
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Mockito.verify(activityService, Mockito.times(1)).deleteActivity(USERNAME, ACTIVITY_ID);
    }

    private static ActivityDTO buildActivity() {
        return new ActivityDTO(ACTIVITY_ID, ACTIVITY_NAME, LocalDate.now(), ACTIVITY_DURATION);
    }
}
