package com.portfolio.moveandgroove.activity.service;

import com.portfolio.moveandgroove.activity.model.Activity;
import com.portfolio.moveandgroove.activity.model.ActivityDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.function.Function;

@Service
public class ActivityDTOMapper implements Function<Activity, ActivityDTO> {
    @Override
    public ActivityDTO apply(final Activity activity) {
        final Long activityId = activity.getId();
        final String activityName = activity.getName();
        final LocalDate activityCreationDate = activity.getCreationDate();
        final LocalTime activityDuration = activity.getDuration();
        return new ActivityDTO(activityId, activityName, activityCreationDate, activityDuration);
    }
}
