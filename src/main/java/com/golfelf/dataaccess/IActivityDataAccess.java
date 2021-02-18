package com.golfelf.dataaccess;

import com.golfelf.drivingrange.Activity;

import java.time.LocalDateTime;
import java.util.List;

public interface IActivityDataAccess {
    void create(Activity activity) throws Exception;

    List<Activity> getAllActivities() throws Exception;

    Activity getActivity(int id) throws Exception;

    Activity getActivityByDate(LocalDateTime dateTime) throws Exception;

    Activity updateActivity(Activity activity) throws Exception;
}
