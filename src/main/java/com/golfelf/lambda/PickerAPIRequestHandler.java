package com.golfelf.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.golfelf.dataaccess.IPickerDataAccess;
import com.golfelf.dataaccess.PickerDataAccess;
import com.golfelf.drivingrange.Picker;
import com.google.gson.Gson;

import java.sql.SQLException;
import java.util.List;

public class PickerAPIRequestHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
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
                case "DELETE":
                    response = processDelete(apiGatewayProxyRequestEvent, context);
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
        Picker p = gsonObj.fromJson(body, Picker.class);

        IPickerDataAccess pickerDataAccess = new PickerDataAccess();

        try {
            pickerDataAccess.create(p);
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
        String path = request.getPath();
        Gson gsonObj = new Gson();
        IPickerDataAccess pickerDataAccess = new PickerDataAccess();

        try {
            if (path.endsWith("/picker")) {
                List<Picker> pickers = pickerDataAccess.getAllPickers();
                response.setBody(gsonObj.toJson(pickers));
            } else {
                Integer id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));
                Picker p = pickerDataAccess.getPicker(id);
                response.setBody(gsonObj.toJson(p));
            }
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
        IPickerDataAccess pickerDataAccess = new PickerDataAccess();

        try {
            Picker p = gsonObj.fromJson(body, Picker.class);
            pickerDataAccess.updatePicker(p);
            response.setBody(gsonObj.toJson(p));
            response.setStatusCode(200);
        } catch (SQLException e) {
            response.setBody(e.getStackTrace().toString());
            response.setStatusCode(400);
        } finally {
            return response;
        }
    }

    private APIGatewayProxyResponseEvent processDelete(APIGatewayProxyRequestEvent request,
                                                       Context context) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        String path = request.getPath();
        Gson gsonObj = new Gson();
        IPickerDataAccess pickerDataAccess = new PickerDataAccess();

        try {
            Integer id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));
            pickerDataAccess.deletePicker(id);
            response.setStatusCode(200);
        } catch (SQLException e) {
            response.setBody(e.getStackTrace().toString());
            response.setStatusCode(400);
        } finally {
            return response;
        }
    }
}
