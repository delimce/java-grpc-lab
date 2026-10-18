package com.delimce.grpc;

import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

class GrpcApplicationMainTest {

    @Test
    void mainStartsSpringApplication() {
        String[] args = { "--spring.main.web-application-type=none" };

        try (MockedStatic<SpringApplication> springApplication = mockStatic(SpringApplication.class)) {
            GrpcApplication.main(args);

            springApplication.verify(() -> SpringApplication.run(GrpcApplication.class, args));
        }
    }
}
