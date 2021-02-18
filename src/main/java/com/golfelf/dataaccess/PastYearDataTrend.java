package com.golfelf.dataaccess;

import com.golfelf.util.DBConnectionManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.log4j.Logger;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class PastYearDataTrend extends DataTrend{
    private Logger logger = Logger.getLogger("PastYearDataTrend");

    @Override
    public void getTrendData() throws Exception {
        ballCountData = new HashMap<String, Integer>();
        activityTimeData = new HashMap<String, Integer>();

        try {
            Connection conn = DBConnectionManager.dbConnection;
            String query = "SELECT TO_CHAR(activity_date, 'Mon') as month," +
                    " TO_CHAR(AVG(ball_count), '9999') as ball_count," +
                    " AVG((DATE_PART('day', end_time::timestamp - start_time::timestamp) * 24 +" +
                    " DATE_PART('hour', end_time::timestamp - start_time::timestamp)) * 60 + " +
                    " DATE_PART('minute', end_time::timestamp - start_time::timestamp)" +
                    " ) as minutes" +
                    " FROM driving_range.activity " +
                    " WHERE activity_date NOW() - interval '1 year'" +
                    " GROUP BY TO_CHAR(activity_date, 'Mon')";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ballCountData.put(rs.getString(1), rs.getInt(2));
                activityTimeData.put(rs.getString(1), rs.getInt(3));
            }
        } catch (Exception e) {
            logger.warn(e);
            throw e;
        }
    }
}
