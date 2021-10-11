# Car Rental Application

## Description
Car Rental Application is an exemplary microservices application developed for research purposes. This microservices architecture includes modern microservices features like Discovery Server, Config Server and API Gateway. The application works in a discovery-first paradigm. All the microservices are build in Kotlin with Springboot framework.

From the business perspective, this is a simple application that serves car rent queries with demo data.

## Services
- Discovery Service
    - Acts as the registry service. All the other services register themselves with this service. The registered services are discoverable by the other services.
- API Gateway
    - Works as the entrypoint for the exposed REST APIs. Delegates the incoming requests to the corresponding services.
- Car Service
    - A business service that processes and serves car queries.
- Location Service
    - A business service that processes and serves location queries.
- Rent Service
    - A business service that processes and serves rent queries.

## Deployment
The application is deployable as docker container with docker compose file.

## Testing
The codebase includes a Postman request collection for testing.
```
~/Car Rental Application API Requests.postman_collection.json
```