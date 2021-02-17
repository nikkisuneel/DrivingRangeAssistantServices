package com.golfelf.dataaccess;

import com.golfelf.drivingrange.Activity;

import java.sql.SQLException;
import java.util.List;

public interface IActivityDataAccess {
    void create(Activity activity) throws Exception;

    List<Activity> getAllActivities() throws Exception;

    Activity updateActivity(Activity activity) throws Exception;
}
