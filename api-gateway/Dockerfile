#FROM openjdk:16-slim
FROM maven:3.8.2-openjdk-16 AS build-env

#making work directory
RUN mkdir -p /usr/src/app/services/api-gateway

#defining work directory
WORKDIR /usr/src/app/services/api-gateway


#copy to the Project Directory
COPY  ./api-gateway /usr/src/app/services/api-gateway


#building app
RUN mvn clean package

#assigning entry point
ENTRYPOINT ["java", "-jar", "/usr/src/app/services/api-gateway/target/api-gateway-0.0.1-SNAPSHOT.jar"]