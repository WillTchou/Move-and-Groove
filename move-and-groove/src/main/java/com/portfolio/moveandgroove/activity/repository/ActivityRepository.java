/*package com.portfolio.moveandgroove.activity.repository;

import com.portfolio.moveandgroove.activity.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity,Long> {

    @Query("SELECT a FROM Activity a WHERE a.user_id = :userId")
    Optional<Activity> findActivitiesForUser(@Param("userId") final Long userId);
    @Query("SELECT a FROM Activity a WHERE a.user_id = :userId AND a.id = :activityId")
    Optional<Activity> findActivityForUserById(final Long userId,
                                               final Long activityId);
}
*/