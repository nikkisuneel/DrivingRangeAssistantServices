## Driving Range Golfelf's server side implementation

This is the implementation of the com.golfelf server-side components. The server-side implementation uses AWS' api-gateway, lambda and RDS (Postgres) to provide APIs.

A mobile app developed in Flutter for iOS that uses these APIs are available at https://github.com/nikkisuneel/driving-range-assistant-ui.

### Architecture

![Architecture](images/architecture.png)

### Deployment

The following deployment steps are needed
- Login to AWS management console. Perform the following steps using the console.
- Create a new API in AWS API gateway. See https://docs.aws.amazon.com/apigateway/latest/developerguide/welcome.html
- Use the openapi.yaml to create the resources and their corresponding methods
- Configure each of the resource methods to use their corresponding Lambda functions. See https://docs.aws.amazon.com/apigateway/latest/developerguide/set-up-lambda-integrations.html
  * For /activities API methods, integrate with ActivityManagement Lambda function
  * For /pickers API methods, integrate with PickerManagement Lambda function
  * For /trends API methods, integrate with TrendGeneration Lambda function  
- Navigate to the Lambda console and create the following functions. For all the functions, upload the DrivingRangeAssistantServices.jar for the function code.
    * ActivityManagement
      * Use com.golfelf.lambda.ActivityAPIRequestHandler::handleRequest as the Handler Runtime Setting
    * PickerManagement
      * Use com.golfelf.lambda.PickerAPIRequestHandler::handleRequest as the Handler Runtime Setting
    * TrendGeneration
      * Use com.golfelf.lambda.DataTrendAPIRequestHandler::handleRequest as the Handler Runtime Setting
- Navigate to Database -> RDS and create a Postgres database using instructions at https://aws.amazon.com/getting-started/tutorials/create-connect-postgresql-db/
  Use the following scripts under the src/main/sql directory to build the database.
    * Create Database: create_database.sql
    * Create Schema: create_schema.sql
    * Create tables: create_table_activity.sql
        * create_table_activity.sql
        * create_table_picker.sql
- Download pgAdmin from https://www.pgadmin.org/download/
- Using pgAdmin, run the src/main/sql/create_table_activity.sql and src/main/sql/create_table_picker.sql scripts to create the activity and picker tables
