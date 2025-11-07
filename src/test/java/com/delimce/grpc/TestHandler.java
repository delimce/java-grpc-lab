package com.delimce.grpc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.github.javafaker.Faker;

@SpringBootTest
@ActiveProfiles("test")
public abstract class TestHandler {
    private static final Faker faker = new Faker();



    public static Faker faker() {
        return faker;
    }

    public void setUp() {
        purgeDatabase();
    }

    private void purgeDatabase() {
        System.out.println("Database purged before test execution");
    }
}
