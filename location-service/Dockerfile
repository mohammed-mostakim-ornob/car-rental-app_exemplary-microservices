#FROM openjdk:16-slim
FROM maven:3.8.2-openjdk-16 AS build-env

#making work directory
RUN mkdir -p /usr/src/app/services/location-service

#defining work directory
WORKDIR /usr/src/app/services/location-service


#copy to the Project Directory
COPY  ./location-service /usr/src/app/services/location-service


#building app
RUN mvn clean package

#assigning entry point
ENTRYPOINT ["java", "-jar", "/usr/src/app/services/location-service/target/location-service-0.0.1-SNAPSHOT.jar"]