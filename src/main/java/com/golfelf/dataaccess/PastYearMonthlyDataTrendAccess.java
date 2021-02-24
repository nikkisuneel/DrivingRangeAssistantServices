package com.golfelf.dataaccess;

import com.golfelf.util.DBConnectionManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class PastYearMonthlyDataTrendAccess extends DataTrendAccess {
    private Logger logger = Logger.getLogger("PastYearDataTrend");

    @Override
    public void getTrendData() throws Exception {
        ballCountData = new LinkedHashMap<String, Integer>();
        activityTimeData = new LinkedHashMap<String, Integer>();

        try {
            Connection conn = DBConnectionManager.dbConnection;
            String query = "SELECT date_trunc('Month', activity_date) as month, "  +
                    " date_trunc('Year', activity_date) as Year, " +
                    " TO_CHAR(AVG(ball_count), '9999') as ball_count," +
                    " AVG((DATE_PART('day', end_time::timestamp - start_time::timestamp) * 24 +" +
                    " DATE_PART('hour', end_time::timestamp - start_time::timestamp)) * 60 + " +
                    " DATE_PART('minute', end_time::timestamp - start_time::timestamp)" +
                    " ) as minutes" +
                    " FROM driving_range.activity " +
                    " WHERE activity_date >= NOW() at time zone 'PST' - interval '11 months'" +
                    " GROUP BY date_trunc('Month', activity_date), " +
                             " date_trunc('Year', activity_date) " +
                    " ORDER BY date_trunc('Month', activity_date), " +
                             " date_trunc('Year', activity_date) ";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Date date = new Date(rs.getTimestamp(1).getTime());
                DateFormat formatter = new SimpleDateFormat("MMM");
                String monthOfYear = formatter.format(date);

                ballCountData.put(monthOfYear, Integer.parseInt(rs.getString(3).trim()));
                activityTimeData.put(monthOfYear, rs.getInt(4));
            }
        } catch (Exception e) {
            logger.warn(e);
            throw e;
        }
    }
}
