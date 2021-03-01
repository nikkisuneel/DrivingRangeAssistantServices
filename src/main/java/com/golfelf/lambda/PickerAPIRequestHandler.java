package com.golfelf.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.golfelf.dataaccess.IPickerDataAccess;
import com.golfelf.dataaccess.PickerSQLDataAccess;
import com.golfelf.drivingrange.Picker;
import com.google.gson.Gson;

import java.util.List;
import java.util.Locale;

public class PickerAPIRequestHandler implements RequestHandler<APIGatewayProxyRequestEvent,
        APIGatewayProxyResponseEvent> {

    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request,
                                                      Context context) {
        LambdaLogger logger = context.getLogger();
        APIGatewayProxyResponseEvent response = null;
        try {
            switch(request.getHttpMethod().toUpperCase(Locale.ROOT)) {
                case "POST":
                    response = processPost(request, context);
                    break;
                case "GET":
                    response = processGet(request, context);
                    break;
                case "PUT":
                    response = processPut(request, context);
                    break;
                case "DELETE":
                    response = processDelete(request, context);
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
        Gson gsonObj = new Gson();
        try {
            IPickerDataAccess pickerDataAccess = new PickerSQLDataAccess();
            String body = request.getBody();
            Picker p = gsonObj.fromJson(body, Picker.class);
            pickerDataAccess.create(p);

            Picker addedPicker = pickerDataAccess.getPickerByName(p.getName());

            response.setBody(gsonObj.toJson(addedPicker));

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
        Gson gsonObj = new Gson();

        try {
            String path = request.getPath();
            IPickerDataAccess pickerDataAccess = new PickerSQLDataAccess();

            if (path.endsWith("/pickers")) {
                List<Picker> pickers = pickerDataAccess.getAllPickers();
                logger.log("response bod: " + gsonObj.toJson(pickers));
                response.setBody(gsonObj.toJson(pickers));
            } else {
                Integer id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));
                Picker p = pickerDataAccess.getPicker(id);
                logger.log("response body: " + gsonObj.toJson(p));
                response.setBody(gsonObj.toJson(p));
            }
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
        Gson gsonObj = new Gson();

        try {
            IPickerDataAccess pickerDataAccess = new PickerSQLDataAccess();
            String body = request.getBody();
            String id = request.getPathParameters().get("pickerId");
            Picker p = gsonObj.fromJson(body, Picker.class);
            p.setId(Integer.parseInt(id));
            pickerDataAccess.updatePicker(p);
            Picker updatedPicker = pickerDataAccess.getPicker(Integer.parseInt(id));
            response.setBody(gsonObj.toJson(updatedPicker));
            response.setStatusCode(200);
        } catch (Exception e) {
            logger.log(gsonObj.toJson(e));
            response.setBody(gsonObj.toJson(e));
            response.setStatusCode(400);
        } finally {
            return response;
        }
    }

    private APIGatewayProxyResponseEvent processDelete(APIGatewayProxyRequestEvent request,
                                                       Context context) {
        LambdaLogger logger = context.getLogger();
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        Gson gsonObj = new Gson();

        try {
            String path = request.getPath();
            IPickerDataAccess pickerDataAccess = new PickerSQLDataAccess();
            Integer id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));
            pickerDataAccess.deletePicker(id);
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
