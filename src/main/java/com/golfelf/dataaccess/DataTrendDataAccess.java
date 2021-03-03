package com.golfelf.dataaccess;

import java.sql.SQLException;
import java.util.Map;

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
