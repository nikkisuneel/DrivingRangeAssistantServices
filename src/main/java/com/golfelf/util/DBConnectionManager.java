/*
 * Copyright (c) 2021. Nikhila (Nikki) Suneel. All Rights Reserved.
 */

package com.golfelf.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class DBConnectionManager {
    public static Connection dbConnection;
    private static Logger logger = Logger.getLogger("DBConnectionManager");

    static {
        try {
            Class.forName("org.postgresql.Driver");
            String dbName = "driving_range";
            String userName = "xxxxxxx";
            String password = "xxxxxxx";
            String hostname = "sp-driving-range.ch1tjgsx0t79.us-east-1.rds.amazonaws.com";
            String port = "5432";
            String jdbcUrl = "jdbc:postgresql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password;
            logger.info("Getting remote connection with connection string.");
            dbConnection = DriverManager.getConnection(jdbcUrl);
            logger.info("Remote connection successful.");
        }
        catch (ClassNotFoundException e) {
            logger.warn(e.toString());}
        catch (SQLException e) {
            logger.warn(e);
        }
    }
}
