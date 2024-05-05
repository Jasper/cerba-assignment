# Cerba Research Assigment
Cerba Research assignment project by Jasper Adam

## Database Configuration
To run the application you will need a PostgresQL server running locally.

The application uses the following properties to connect to the database:

- Database name = Cerba
- Username = postgres
- Password = postgres

You can change these in application-local.properties.

## Application tests
The application contains tests for the web and service layer.
You can run these using the maven test command.

## Running the application
You can run the application from IntelliJ. You will need to set the active profile to "local" so the application-local.properties file is loaded.

There is a postman collection in the ".postman" directory.
This contains a collection of API requests you can import into Postman to test the application functionally.