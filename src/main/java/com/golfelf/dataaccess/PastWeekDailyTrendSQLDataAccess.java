/*
 * Copyright (c) 2021. Nikhila (Nikki) Suneel. All Rights Reserved.
 */

package com.golfelf.dataaccess;

import com.golfelf.util.DBConnectionManager;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;

/*
 * An implementation using SQL to retrieve the past week's ball count and activity time data
 */
public class PastWeekDailyTrendSQLDataAccess extends DataTrendDataAccess {

    @Override
    public void getTrendData() throws SQLException {
        ballCountData = new LinkedHashMap<String, Integer>();
        activityTimeData = new LinkedHashMap<String, Integer>();

        Connection conn = DBConnectionManager.dbConnection;
        String query = "SELECT date_trunc('day', activity_date) as day," +
                " TO_CHAR(AVG(ball_count), '9999') as ball_count," +
                " AVG((DATE_PART('day', end_time::timestamp - start_time::timestamp) * 24 +" +
                " DATE_PART('hour', end_time::timestamp - start_time::timestamp)) * 60 + " +
                " DATE_PART('minute', end_time::timestamp - start_time::timestamp)" +
                " ) as minutes" +
                " FROM driving_range.ball_picking_activity " +
                " WHERE activity_date >= NOW() at time zone 'PST' - interval '1 week'" +
                " GROUP BY date_trunc('day', activity_date) " +
                " ORDER BY date_trunc('day', activity_date)";
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Date date = new Date(rs.getTimestamp(1).getTime());
            DateFormat formatter = new SimpleDateFormat("EE");
            String dayOfWeek = formatter.format(date);
            ballCountData.put(dayOfWeek, Integer.parseInt(rs.getString(2).trim()));
            activityTimeData.put(dayOfWeek, rs.getInt(3));
        }
    }
}
