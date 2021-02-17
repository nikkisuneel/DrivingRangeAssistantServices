package com.golfelf.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.golfelf.dataaccess.ActivityDataAccess;
import com.golfelf.dataaccess.IActivityDataAccess;
import com.golfelf.drivingrange.Activity;
import com.google.gson.Gson;

import java.sql.SQLException;
import java.util.List;

public class ActivityAPIRequestHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent,
                                                      Context context) {

        APIGatewayProxyResponseEvent response = null;

        try {
            switch(apiGatewayProxyRequestEvent.getHttpMethod()) {
                case "POST":
                    response = processPost(apiGatewayProxyRequestEvent, context);
                    break;
                case "Get":
                    response = processGet(apiGatewayProxyRequestEvent, context);
                    break;
                case "PUT":
                    response = processPut(apiGatewayProxyRequestEvent, context);
                    break;
                default:
                    response =  new APIGatewayProxyResponseEvent();
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = new APIGatewayProxyResponseEvent();
            response.setStatusCode(500);
        } finally {
            return response;
        }
    }

    private APIGatewayProxyResponseEvent processPost(APIGatewayProxyRequestEvent request,
                                                     Context context) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        String body = request.getBody();

        Gson gsonObj = new Gson();
        Activity a = gsonObj.fromJson(body, Activity.class);

        IActivityDataAccess activityDataAccess = new ActivityDataAccess();

        try {
            activityDataAccess.create(a);
            response.setStatusCode(200);
        } catch (SQLException e) {
            response.setBody(e.getStackTrace().toString());
            response.setStatusCode(400);
        } finally {
            return response;
        }
    }

    private APIGatewayProxyResponseEvent processGet(APIGatewayProxyRequestEvent request,
                                                    Context context) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        Gson gsonObj = new Gson();
        IActivityDataAccess activityDataAccess = new ActivityDataAccess();

        try {
            List<Activity> activities = activityDataAccess.getAllActivities();
            response.setBody(gsonObj.toJson(activities));
            response.setStatusCode(200);
        } catch (SQLException e) {
            response.setBody(e.getStackTrace().toString());
            response.setStatusCode(400);
        } finally {
            return response;
        }
    }

    private APIGatewayProxyResponseEvent processPut(APIGatewayProxyRequestEvent request,
                                                    Context context) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        String body = request.getBody();
        Gson gsonObj = new Gson();
        IActivityDataAccess activityDataAccess = new ActivityDataAccess();

        try {
            Activity a = gsonObj.fromJson(body, Activity.class);
            activityDataAccess.updateActivity(a);
            response.setBody(gsonObj.toJson(a));
            response.setStatusCode(200);
        } catch (SQLException e) {
            response.setBody(e.getStackTrace().toString());
            response.setStatusCode(400);
        } finally {
            return response;
        }
    }
}
