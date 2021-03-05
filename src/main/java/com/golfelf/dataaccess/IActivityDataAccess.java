/*
 * Copyright (c) 2021. Nikhila (Nikki) Suneel. All Rights Reserved.
 */

package com.golfelf.dataaccess;

import com.golfelf.drivingrange.Activity;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public interface IActivityDataAccess {
    void create(Activity activity) throws IllegalArgumentException, SQLException;

    List<Activity> getAllActivities() throws SQLException;

    Activity getActivity(int id) throws IllegalArgumentException, SQLException;

    Activity getActivityByDate(LocalDateTime dateTime) throws IllegalArgumentException, SQLException;

    Activity updateActivity(Activity activity) throws IllegalArgumentException, SQLException;
}
