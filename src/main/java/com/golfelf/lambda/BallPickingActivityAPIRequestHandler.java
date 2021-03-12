/*
 * Copyright (c) 2021. Nikhila (Nikki) Suneel. All Rights Reserved.
 */

package com.golfelf.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.golfelf.dataaccess.BallPickingActivitySQLDataAccess;
import com.golfelf.dataaccess.IBallPickingActivityDataAccess;
import com.golfelf.drivingrange.BallPickingActivity;
import com.golfelf.util.Utils;
import com.google.gson.Gson;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

/*
 * A Lambda handler for processing Ball Picking Activity APIs
 */
public class BallPickingActivityAPIRequestHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent,
                                                      Context context) {
        LambdaLogger logger = context.getLogger();
        APIGatewayProxyResponseEvent response = null;

        try {
            switch(apiGatewayProxyRequestEvent.getHttpMethod().toUpperCase(Locale.ROOT)) {
                case "POST":
                    response = processPost(apiGatewayProxyRequestEvent, context);
                    break;
                case "GET":
                    response = processGet(apiGatewayProxyRequestEvent, context);
                    break;
                case "PUT":
                    response = processPut(apiGatewayProxyRequestEvent, context);
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

    private APIGatewayProxyResponseEvent processPost(APIGatewayProxyRequestEvent request,
                                                     Context context) {
        LambdaLogger logger = context.getLogger();
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        Gson gsonObj = Utils.getGsonWithFormatters();

        try {
            String body = request.getBody();
            BallPickingActivity inputBallPickingActivity = gsonObj.fromJson(body, BallPickingActivity.class);
            IBallPickingActivityDataAccess activityDataAccess = new BallPickingActivitySQLDataAccess();
            activityDataAccess.create(inputBallPickingActivity);
            BallPickingActivity createdBallPickingActivity = activityDataAccess.getActivityByDate(inputBallPickingActivity.getActivityDate());
            response.setBody(gsonObj.toJson(createdBallPickingActivity));
            response.setStatusCode(200);
        } catch (IllegalArgumentException e) {
            logger.log(gsonObj.toJson(e));
            response.setBody(gsonObj.toJson(e.getMessage()));
            response.setStatusCode(400);
        } catch (SQLException e) {
            logger.log(gsonObj.toJson(e));
            response.setBody(gsonObj.toJson(e.getMessage()));
            response.setStatusCode(500);
        } finally {
            return response;
        }
    }

    private APIGatewayProxyResponseEvent processGet(APIGatewayProxyRequestEvent request,
                                                    Context context) {
        LambdaLogger logger = context.getLogger();
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        Gson gsonObj = Utils.getGsonWithFormatters();

        try {
            IBallPickingActivityDataAccess activityDataAccess = new BallPickingActivitySQLDataAccess();
            List<BallPickingActivity> activities = activityDataAccess.getAllActivities();
            response.setBody(gsonObj.toJson(activities));
            response.setStatusCode(200);
        } catch (SQLException e) {
            logger.log(gsonObj.toJson(e));
            response.setBody(gsonObj.toJson(e.getMessage()));
            response.setStatusCode(500);
        } finally {
            return response;
        }
    }

    private APIGatewayProxyResponseEvent processPut(APIGatewayProxyRequestEvent request,
                                                    Context context) {
        LambdaLogger logger = context.getLogger();
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        Gson gsonObj = Utils.getGsonWithFormatters();

        try {
            String body = request.getBody();
            IBallPickingActivityDataAccess activityDataAccess = new BallPickingActivitySQLDataAccess();
            String id = request.getPathParameters().get("activityId");
            BallPickingActivity a = gsonObj.fromJson(body, BallPickingActivity.class);
            a.setId(Integer.parseInt(id));
            activityDataAccess.updateActivity(a);
            BallPickingActivity updatedBallPickingActivity = activityDataAccess.getActivity(Integer.parseInt(id));
            response.setBody(gsonObj.toJson(updatedBallPickingActivity));
            response.setStatusCode(200);
        } catch (IllegalArgumentException e) {
            logger.log(gsonObj.toJson(e));
            response.setBody(gsonObj.toJson(e.getMessage()));
            response.setStatusCode(400);
        } catch (SQLException e) {
            logger.log(gsonObj.toJson(e));
            response.setBody(gsonObj.toJson(e.getMessage()));
            response.setStatusCode(500);
        } finally {
            return response;
        }
    }
}
