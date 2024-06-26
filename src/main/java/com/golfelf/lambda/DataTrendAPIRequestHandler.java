/*
 * Copyright (c) 2021. Nikhila (Nikki) Suneel. All Rights Reserved.
 */

package com.golfelf.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.golfelf.dataaccess.*;
import com.google.gson.Gson;

import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;

/*
 * A Lambda handler for processing Data Trend APIs
 */
public class DataTrendAPIRequestHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent,
                                                      Context context) {
        LambdaLogger logger = context.getLogger();
        APIGatewayProxyResponseEvent response = null;

        try {
            switch(apiGatewayProxyRequestEvent.getHttpMethod().toUpperCase(Locale.ROOT)) {
                case "GET":
                    response = processGet(apiGatewayProxyRequestEvent, context);
                    break;
                default:
                    response =  new APIGatewayProxyResponseEvent();
            }
        } catch (Exception e) {
            logger.log(e.getMessage());
            response = new APIGatewayProxyResponseEvent();
            response.setBody(e.getMessage());
            response.setStatusCode(500);
        } finally {
            return response;
        }
    }

    private APIGatewayProxyResponseEvent processGet(APIGatewayProxyRequestEvent request,
                                                    Context context) {
        LambdaLogger logger = context.getLogger();
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        Gson gsonObj = new Gson();

        try {
            DataTrendDataAccess dailyDataTrendAccess = new PastWeekDailyTrendSQLDataAccess();
            DataTrendDataAccess monthlyDataTrendAccess = new PastYearMonthlyDataTrendSQLDataAccess();

            dailyDataTrendAccess.getTrendData();
            monthlyDataTrendAccess.getTrendData();

            Map<String, Integer> dailyBallCountData = dailyDataTrendAccess.getBallCountData();
            Map<String, Integer> dailyActivityTimeData = dailyDataTrendAccess.getActivityTimeData();
            Map<String, Integer> monthlyBallCountData = monthlyDataTrendAccess.getBallCountData();
            Map<String, Integer> monthlyActivityTimeData = monthlyDataTrendAccess.getActivityTimeData();

            DataTrendObject dtObj = new DataTrendObject();
            dtObj.monthly.ballCountData= monthlyBallCountData;
            dtObj.monthly.activityTimeData = monthlyActivityTimeData;
            dtObj.daily.ballCountData = dailyBallCountData;
            dtObj.daily.activityTimeData = dailyActivityTimeData;

            response.setBody(gsonObj.toJson(dtObj));

            response.setStatusCode(200);
        } catch (SQLException e) {
            logger.log(gsonObj.toJson(e));
            response.setBody(gsonObj.toJson(e.getMessage()));
            response.setStatusCode(500);
        } finally {
            return response;
        }
    }

    class TrendData {
        Map<String, Integer> ballCountData;
        Map<String, Integer> activityTimeData;
    }

    class DataTrendObject {
        TrendData monthly;
        TrendData daily;

        DataTrendObject() {
            this.monthly = new TrendData();
            this.daily = new TrendData();
        }
    }
}