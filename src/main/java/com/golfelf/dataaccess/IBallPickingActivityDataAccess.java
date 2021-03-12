/*
 * Copyright (c) 2021. Nikhila (Nikki) Suneel. All Rights Reserved.
 */

package com.golfelf.dataaccess;

import com.golfelf.drivingrange.BallPickingActivity;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/*
 * An interface that defines the methods to access Ball Picking Activity data from storage
 */
public interface IBallPickingActivityDataAccess {
    void create(BallPickingActivity ballPickingActivity) throws IllegalArgumentException, SQLException;

    List<BallPickingActivity> getAllActivities() throws IllegalArgumentException, SQLException;

    BallPickingActivity getActivity(int id) throws IllegalArgumentException, SQLException;

    BallPickingActivity getActivityByDate(LocalDateTime dateTime) throws IllegalArgumentException, SQLException;

    BallPickingActivity updateActivity(BallPickingActivity ballPickingActivity) throws IllegalArgumentException, SQLException;
}
