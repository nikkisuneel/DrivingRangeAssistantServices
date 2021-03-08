/*
 * Copyright (c) 2021. Nikhila (Nikki) Suneel. All Rights Reserved.
 */

package com.golfelf.drivingrange;

import java.time.LocalDateTime;
import java.util.Map;

public class Activity {
    private int id;
    private LocalDateTime activityDate;
    private int ballCount;
    private Map<String, Integer> pickerCounts;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Activity() {}

    public Activity(LocalDateTime activityDate,
                    int ballCount,
                    Map<String, Integer> pickerCounts) {
        setActivityDate(activityDate);
        setBallCount(ballCount);
        setPickerCounts(pickerCounts);
    }

    public Activity(LocalDateTime activityDate,
                    int ballCount,
                    Map<String, Integer> pickerCounts,
                    LocalDateTime startTime,
                    LocalDateTime endTime) {
        this(activityDate, ballCount, pickerCounts);
        setStartTime(startTime);
        setEndTime(endTime);
    }

    public Activity(int id,
                    LocalDateTime activityDate,
                    int ballCount,
                    Map<String, Integer> pickerCounts,
                    LocalDateTime startTime,
                    LocalDateTime endTime) {
        this(activityDate, ballCount, pickerCounts, startTime, endTime);
        setId(id);
    }

    public LocalDateTime getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(LocalDateTime activityDate) {
        if (activityDate == null) {
            throw new IllegalArgumentException("activityDate must not be null");
        }
        this.activityDate = activityDate;
    }

    public int getBallCount() {
        return ballCount;
    }

    public void setBallCount(int ballCount) {
        this.ballCount = ballCount;
    }

    public Map<String, Integer> getPickerCounts() {
        return pickerCounts;
    }

    public void setPickerCounts(Map<String, Integer> pickerCounts) {
        if (pickerCounts == null) {
            throw new IllegalArgumentException("pickerCounts must not be null");
        }
        if (pickerCounts.isEmpty()) {
            throw new IllegalArgumentException("pickerCounts must not be empty");
        }
        this.pickerCounts = pickerCounts;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        if (startTime == null) {
            throw new IllegalArgumentException("startTime must not be null");
        }
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Activity)) {
            return false;
        }
        Activity a = (Activity) obj;

        if (this.id == a.getId()) {
            return true;
        }
        return false;
    }
}
