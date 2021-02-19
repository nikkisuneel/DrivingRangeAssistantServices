package com.golfelf.dataaccess;

import java.util.Map;

public abstract class DataTrendAccess {
    protected Map<String, Integer> ballCountData;
    protected Map<String, Integer> activityTimeData;

    public abstract void getTrendData() throws Exception;

    public Map<String, Integer> getBallCountData() {
        return ballCountData;
    }

    public Map<String, Integer> getActivityTimeData() {
        return activityTimeData;
    }
}
