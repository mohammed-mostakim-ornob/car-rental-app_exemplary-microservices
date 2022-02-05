#FROM openjdk:16-slim
FROM maven:3.8.2-openjdk-17 AS build-env

#making work directory
RUN mkdir -p /usr/src/app/services/management-service

#defining work directory
WORKDIR /usr/src/app/services/management-service


#copy to the Project Directory
COPY ./management-service /usr/src/app/services/management-service


#building app
RUN mvn clean package

#assigning entry point
ENTRYPOINT ["java", "-jar", "/usr/src/app/services/management-service/target/management-service-0.0.1-SNAPSHOT.jar"]