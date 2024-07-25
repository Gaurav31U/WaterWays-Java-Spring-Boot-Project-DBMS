FROM maven:3.8.5-openjdk-17 as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
RUN apt-get update && \
    apt-get install -y default-mysql-client && \
    rm -rf /var/lib/apt/lists/*
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
COPY src/main/resources/sql /app/sql
COPY mysql.sh mysql.sh
RUN chmod +x mysql.sh
EXPOSE 8080
ENTRYPOINT ["./mysql.sh"]
CMD ["java", "-jar", "app.jar"]
