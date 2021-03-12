/*
 * Copyright (c) 2021. Nikhila (Nikki) Suneel. All Rights Reserved.
 */

package com.golfelf.dataaccess;

import java.lang.reflect.Type;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.golfelf.drivingrange.BallPickingActivity;
import com.golfelf.util.DBConnectionManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/*
 * An implementation of the IBallPickingActivityDataAccess interface for activity data access from SQL
 */
public class BallPickingActivitySQLDataAccess implements IBallPickingActivityDataAccess {

    @Override
    public void create(BallPickingActivity ballPickingActivity) throws IllegalArgumentException, SQLException {
        if (ballPickingActivity == null) {
            throw new IllegalArgumentException("ballPickingActivity cannot be null");
        }

        Connection conn = DBConnectionManager.dbConnection;
        String insertStatement = "INSERT INTO driving_range.ball_picking_activity" +
                " (activity_date, ball_count, picker_counts, start_time, end_time)" +
                " VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(insertStatement);
        stmt.setTimestamp(1, Timestamp.valueOf(ballPickingActivity.getActivityDate()));
        stmt.setInt(2, ballPickingActivity.getBallCount());
        Gson gsonObj = new Gson();
        stmt.setString(3, gsonObj.toJson(ballPickingActivity.getPickerCounts()));
        if (ballPickingActivity.getStartTime() == null) {
            stmt.setNull(4, Types.NULL);
        } else {
            stmt.setTimestamp(4, Timestamp.valueOf(ballPickingActivity.getStartTime()));
        }
        if (ballPickingActivity.getEndTime() == null) {
            stmt.setNull(5, Types.NULL);
        } else {
            stmt.setTimestamp(5, Timestamp.valueOf(ballPickingActivity.getEndTime()));
        }
        stmt.executeUpdate();
    }

    @Override
    public List<BallPickingActivity> getAllActivities() throws IllegalArgumentException, SQLException {
        List<BallPickingActivity> result = new ArrayList<>();

        Connection conn = DBConnectionManager.dbConnection;
        String query = "SELECT id, activity_date, ball_count, picker_counts, start_time, end_time" +
                " FROM driving_range.ball_picking_activity" +
                " ORDER BY activity_date asc ";
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        Gson gsonObj = new Gson();
        Type mapType = new TypeToken<Map<String, Integer>>() {}.getType();
        while(rs.next()) {
            BallPickingActivity p = new BallPickingActivity(rs.getInt(1),
                    rs.getTimestamp(2).toLocalDateTime(),
                    rs.getInt(3),
                    gsonObj.fromJson(rs.getString(4), mapType),
                    rs.getTimestamp(5) == null ? null :rs.getTimestamp(5).toLocalDateTime(),
                    rs.getTimestamp(6) ==null ? null : rs.getTimestamp(6).toLocalDateTime()
            );

            result.add(p);
        }

        return result;
    }

    @Override
    public BallPickingActivity getActivity(int id) throws IllegalArgumentException, SQLException {
        BallPickingActivity result = new BallPickingActivity();

        Connection conn = DBConnectionManager.dbConnection;
        String query = "SELECT id, activity_date, ball_count, picker_counts, start_time, end_time" +
                " FROM driving_range.ball_picking_activity" +
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
        } else {
            throw new IllegalArgumentException("No activity found for " + id);
        }

        return result;
    }

    @Override
    public BallPickingActivity getActivityByDate(LocalDateTime dateTime) throws IllegalArgumentException, SQLException {
        BallPickingActivity result = new BallPickingActivity();

        if (dateTime == null) {
            throw new IllegalArgumentException("dateTime cannot be null");
        }
        Connection conn = DBConnectionManager.dbConnection;
        String query = "SELECT id, activity_date, ball_count, picker_counts, start_time, end_time" +
                " FROM driving_range.ball_picking_activity" +
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
        } else {
            throw new IllegalArgumentException("No activity found for " + dateTime.format(DateTimeFormatter.ISO_DATE));
        }

        return result;
    }

    @Override
    public BallPickingActivity updateActivity(BallPickingActivity ballPickingActivity) throws IllegalArgumentException, SQLException {
        Connection conn = DBConnectionManager.dbConnection;
        Gson gsonObj = new Gson();

        String updateStatement = "UPDATE driving_range.ball_picking_activity " +
                " SET activity_date = ?, " +
                    " ball_count = ?, " +
                    " picker_counts = ?, " +
                    " start_time = ?, " +
                    " end_time = ? " +
                " WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(updateStatement);

        stmt.setTimestamp(1, Timestamp.valueOf(ballPickingActivity.getActivityDate()));
        stmt.setInt(2, ballPickingActivity.getBallCount());
        stmt.setString(3, gsonObj.toJson(ballPickingActivity.getPickerCounts()));

        if (ballPickingActivity.getStartTime() == null) {
            stmt.setNull(4, Types.NULL);
        } else {
            stmt.setTimestamp(4, Timestamp.valueOf(ballPickingActivity.getStartTime()));
        }

        if (ballPickingActivity.getEndTime() == null) {
            stmt.setNull(5, Types.NULL);
        } else {
            stmt.setTimestamp(5, Timestamp.valueOf(ballPickingActivity.getEndTime()));
        }
        stmt.setInt(6, ballPickingActivity.getId());
        stmt.executeUpdate();

        return ballPickingActivity;
    }
}
