package com.portfolio.moveandgroove.Activity.service;

import com.portfolio.moveandgroove.activity.model.Activity;
import com.portfolio.moveandgroove.activity.model.ActivityDTO;
import com.portfolio.moveandgroove.activity.service.ActivityDTOMapper;
import com.portfolio.moveandgroove.user.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;

@ExtendWith(MockitoExtension.class)
public class ActivityMapperTest {

    public static final String NAME = "basketball";
    public static final long ID = 1L;
    public static final LocalTime DURATION = LocalTime.NOON;
    private ActivityDTOMapper activityDTOMapper;

    @BeforeEach
    void setup() {
        activityDTOMapper = new ActivityDTOMapper();
    }

    @Test
    void apply() {
        //Given
        final Activity activity = buildActivity();
        final ActivityDTO activityDTO = new ActivityDTO(ID, NAME, LocalDate.now(), DURATION);
        //When
        final ActivityDTO result = activityDTOMapper.apply(activity);
        //Then
        Assertions.assertThat(result)
                .isNotNull()
                .isEqualTo(activityDTO);
    }

    private Activity buildActivity() {
        return new Activity(ID, NAME, DURATION, new User());
    }
}
