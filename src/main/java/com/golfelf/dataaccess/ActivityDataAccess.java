package com.golfelf.dataaccess;

import java.lang.reflect.Type;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.golfelf.util.DBConnectionManager;
import com.golfelf.drivingrange.Activity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.log4j.Logger;

public class ActivityDataAccess implements IActivityDataAccess{
    private Logger logger = Logger.getLogger("PickerDataAccess");

    @Override
    public void create(Activity activity) throws Exception {
        try {
            Connection conn = DBConnectionManager.dbConnection;
            String insertStatement = "INSERT INTO driving_range.activity" +
                    " (activity_date, ball_count, picker_counts, start_time, end_time)" +
                    " VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(insertStatement);
            stmt.setTimestamp(1, Timestamp.valueOf(activity.getActivityDate()));
            stmt.setInt(2, activity.getBallCount());
            Gson gsonObj = new Gson();
            stmt.setString(3, gsonObj.toJson(activity.getPickerCounts()));
            if (activity.getStartTime() == null) {
                stmt.setNull(4, Types.NULL);
            } else {
                stmt.setTimestamp(4, Timestamp.valueOf(activity.getStartTime()));
            }
            if (activity.getEndTime() == null) {
                stmt.setNull(5, Types.NULL);
            } else {
                stmt.setTimestamp(5, Timestamp.valueOf(activity.getEndTime()));
            }
            stmt.executeUpdate();
        } catch (Exception e) {
            logger.warn(e);
            throw e;
        }
    }

    @Override
    public List<Activity> getAllActivities() throws Exception{
        List<Activity> result = new ArrayList<>();
        try {
            Connection conn = DBConnectionManager.dbConnection;
            String query = "SELECT id, activity_date, ball_count, picker_counts, start_time, end_time" +
                    " FROM driving_range.activity" +
                    " ORDER BY activity_date asc ";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            Gson gsonObj = new Gson();
            Type mapType = new TypeToken<Map<String, Integer>>() {}.getType();
            while(rs.next()) {
                Activity p = new Activity(rs.getInt(1),
                        rs.getTimestamp(2).toLocalDateTime(),
                        rs.getInt(3),
                        gsonObj.fromJson(rs.getString(4), mapType),
                        rs.getTimestamp(5) == null ? null :rs.getTimestamp(5).toLocalDateTime(),
                        rs.getTimestamp(6) ==null ? null : rs.getTimestamp(6).toLocalDateTime()
                );
                result.add(p);
            }
        } catch (Exception e) {
            logger.warn(e);
            throw e;
        } finally {
            return result;
        }
    }

    @Override
    public Activity getActivity(int id) throws Exception {
        Activity result = new Activity();
        try {
            Connection conn = DBConnectionManager.dbConnection;
            String query = "SELECT id, activity_date, ball_count, picker_counts, start_time, end_time" +
                    " FROM driving_range.activity" +
                    " WHERE id = ? ";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            Gson gsonObj = new Gson();
            Type mapType = new TypeToken<Map<String, Integer>>() {}.getType();
            if (rs.next()) {
                result.setId(rs.getInt(1));
                result.setActivityDate(rs.getTimestamp(2).toLocalDateTime());
                result.setBallCount(rs.getInt(3));
                result.setPickerCounts(gsonObj.fromJson(rs.getString(4), mapType));
                result.setStartTime(
                        rs.getTimestamp(5) == null
                                ? null
                                : rs.getTimestamp(5).toLocalDateTime()
                );
                result.setEndTime(
                        rs.getTimestamp(6) == null
                                ? null
                                : rs.getTimestamp(6).toLocalDateTime()
                );
            }
        } catch (Exception e) {
            logger.warn(e);
            throw e;
        } finally {
            return result;
        }
    }

    @Override
    public Activity getActivityByDate(LocalDateTime dateTime) {
        Activity result = new Activity();
        try {
            Connection conn = DBConnectionManager.dbConnection;
            String query = "SELECT id, activity_date, ball_count, picker_counts, start_time, end_time" +
                    " FROM driving_range.activity" +
                    " WHERE activity_date = ? ";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setTimestamp(1, Timestamp.valueOf(dateTime));
            ResultSet rs = stmt.executeQuery();
            Gson gsonObj = new Gson();
            Type mapType = new TypeToken<Map<String, Integer>>() {}.getType();
            if (rs.next()) {
                result.setId(rs.getInt(1));
                result.setActivityDate(rs.getTimestamp(2).toLocalDateTime());
                result.setBallCount(rs.getInt(3));
                result.setPickerCounts(gsonObj.fromJson(rs.getString(4), mapType));
                result.setStartTime(
                        rs.getTimestamp(5) == null
                                ? null
                                : rs.getTimestamp(5).toLocalDateTime()
                );
                result.setEndTime(
                        rs.getTimestamp(6) == null
                                ? null
                                : rs.getTimestamp(6).toLocalDateTime()
                );
            }
        } catch (Exception e) {
            logger.warn(e);
            throw e;
        } finally {
            return result;
        }
    }

    @Override
    public Activity updateActivity(Activity activity) throws Exception {
        Activity result = null;
        try {
            Connection conn = DBConnectionManager.dbConnection;
            String updateStatement = "UPDATE driving_range.activity " +
                    " SET start_time = ?, end_time = ?" +
                    " WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(updateStatement);

            if (activity.getStartTime() == null) {
                stmt.setNull(1, Types.NULL);
            } else {
                stmt.setTimestamp(1, Timestamp.valueOf(activity.getStartTime()));
            }
            if (activity.getEndTime() == null) {
                stmt.setNull(2, Types.NULL);
            } else {
                stmt.setTimestamp(2, Timestamp.valueOf(activity.getEndTime()));
            }
            stmt.setInt(3, activity.getId());
            stmt.executeUpdate();
            result = activity;
        } catch (Exception e) {
            logger.warn(e);
            throw e;
        } finally {
            return result;
        }
    }
}
