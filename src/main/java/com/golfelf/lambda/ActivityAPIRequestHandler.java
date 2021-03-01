package com.golfelf.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.golfelf.dataaccess.ActivitySQLDataAccess;
import com.golfelf.dataaccess.IActivityDataAccess;
import com.golfelf.drivingrange.Activity;
import com.golfelf.util.Utils;
import com.google.gson.Gson;

import java.util.List;
import java.util.Locale;

public class ActivityAPIRequestHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
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
            Activity inputActivity = gsonObj.fromJson(body, Activity.class);
            IActivityDataAccess activityDataAccess = new ActivitySQLDataAccess();
            activityDataAccess.create(inputActivity);
            Activity createdActivity = activityDataAccess.getActivityByDate(inputActivity.getActivityDate());
            response.setBody(gsonObj.toJson(createdActivity));
            response.setStatusCode(200);
        } catch (Exception e) {
            logger.log(gsonObj.toJson(e));
            response.setBody(gsonObj.toJson(e));
            response.setStatusCode(400);
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
            IActivityDataAccess activityDataAccess = new ActivitySQLDataAccess();
            List<Activity> activities = activityDataAccess.getAllActivities();
            response.setBody(gsonObj.toJson(activities));
            response.setStatusCode(200);
        } catch (Exception e) {
            logger.log(gsonObj.toJson(e));
            response.setBody(gsonObj.toJson(e));
            response.setStatusCode(400);
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
            IActivityDataAccess activityDataAccess = new ActivitySQLDataAccess();
            String id = request.getPathParameters().get("activityId");
            Activity a = gsonObj.fromJson(body, Activity.class);
            a.setId(Integer.parseInt(id));
            activityDataAccess.updateActivity(a);
            Activity updatedActivity = activityDataAccess.getActivity(Integer.parseInt(id));
            response.setBody(gsonObj.toJson(updatedActivity));
            response.setStatusCode(200);
        } catch (Exception e) {
            logger.log(gsonObj.toJson(e));
            response.setBody(gsonObj.toJson(e));
            response.setStatusCode(400);
        } finally {
            return response;
        }
    }
}
