# WaterWays Project

WaterWays is a Spring Boot project that allows customers to book cruises based on various parameters such as destination, source, time, and date. It also provides food services and has employees who maintain cruise operations.

## Features

- Customer cruise booking based on destination, source, time, date, and other criteria.
- Food service booking on the cruise.
- Employee management for maintaining cruise operations.
- MySQL database integration following MVVM architecture.
- Ensures database transactions follow the ACID properties.
- Search available cruise tours by departure place, destination, and time using MySQL queries.

## Technologies Used

- **Java**: Programming language.
- **Spring Boot**: Framework for building the application.
- **MySQL**: Database for storing information.
- **MVVM Architecture**: For structuring the code.

## Prerequisites

- Java SDK
- MySQL

## Local Setup

1. **Clone the Repository:**
```
git clone https://github.com/Gaurav31U/WaterWays-Java-Spring-Boot-Project-DBMS.git
cd WaterWays-Java-Spring-Boot-Project-DBMS
```
2. **Install Java SDK:**
   - Download and install the Java SDK from the official website.

3. **Install MySQL:**
   - Download and install MySQL from the official website.

4. **Configure Application Properties:**
   - Update the database connection details in the `src/resources/application.properties` file.

5. **Migrate SQL Schema:**
   - Run the SQL scripts located in the `src/resources/sql` directory on your local MySQL database to set up the schema.

6. **Run the Application:**
   - Run the `WaterwaysApplication.java` file to start the application.
   - Server is Running on [http://localhost:8080/](http://localhost:8080/).

## Docker Setup

1. **Run the Docker Compose Command:**
   - Execute `docker-compose up` to set up and run the project using Docker.
   ```
   docker-compose up
   ```
   - Server is Running on [http://localhost:8080/](http://localhost:8080/).

## Additional Details

- The project implements transactions using MySQL queries to ensure database transactions follow the ACID properties.
- Provides functionality for searching available cruise tours by departure place, destination, and time.
- Includes features for food and room booking on the cruise.

For more detailed information and documentation, please refer to the project repository on [GitHub](https://github.com/Gaurav31U/WaterWays-Java-Spring-Boot-Project-DBMS).
