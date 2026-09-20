# Java GRPC Lab
This project is a simple Java application that demonstrates how to set up a gRPC server using Spring Boot. It includes a basic Ping service that responds with a Pong message.

## Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- grpcurl

## Installing grpcurl
Install `grpcurl` directly from the official GitHub release on Linux, or with Homebrew on macOS.

Linux x86_64:
```bash
GRPCURL_VERSION="1.9.1"
curl -L "https://github.com/fullstorydev/grpcurl/releases/download/v${GRPCURL_VERSION}/grpcurl_${GRPCURL_VERSION}_linux_x86_64.tar.gz" -o grpcurl.tar.gz
tar -xzf grpcurl.tar.gz grpcurl
sudo mv grpcurl /usr/local/bin/grpcurl
rm grpcurl.tar.gz
```

macOS:
```bash
brew install grpcurl
```

Verify the installation:
```bash
grpcurl --version
```

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
grpcurl -plaintext localhost:9091 list
```

Test the Ping service:
```bash
grpcurl -plaintext -d '{}' localhost:9091 health.HealthService/Ping
```

## Ready to Launch
Build and start the application:
```bash
./mvnw -DskipTests package
java -jar target/*.jar
```

In another terminal, call the Ping service:
```bash
grpcurl -plaintext -d '{}' localhost:9091 health.HealthService/Ping
```
