# Java GRPC Lab
This project is a simple Java application that demonstrates how to set up a gRPC server using Spring Boot. It includes a basic Ping service that responds with a Pong message.

## Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- grpcurl 

## Building the Application
To build the application, run the following command in the project root directory:
```bash
./mvnw -DskipTests package
```

## Running the Application
You can run the application using the Spring Boot Maven plugin:
```bash
./mvnw spring-boot:run
```

Alternatively, you can run the packaged JAR file:
```bash
java -jar target/*.jar
``` 
## Testing the GRPC Service
Once the server is running, you can test the Ping service using `grpcurl`. First,
list the available services:
```bash
grpcurl -plaintext localhost:9090 list
```

test ping service:
```bash
grpcurl -plaintext -d '{}' localhost:9090 health.PingService/Ping
```