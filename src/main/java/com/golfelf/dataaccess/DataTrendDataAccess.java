/*
 * Copyright (c) 2021. Nikhila (Nikki) Suneel. All Rights Reserved.
 */

package com.golfelf.dataaccess;

import java.sql.SQLException;
import java.util.Map;

/*
 * An abstract class that generalizes the data access properties and methods for
 * retrieving trend data. Ball count and activity time trends are captured in this class
 */
public abstract class DataTrendDataAccess {
    protected Map<String, Integer> ballCountData;
    protected Map<String, Integer> activityTimeData;

    public abstract void getTrendData() throws SQLException;

    public Map<String, Integer> getBallCountData() {
        return ballCountData;
    }

    public Map<String, Integer> getActivityTimeData() {
        return activityTimeData;
    }
}
