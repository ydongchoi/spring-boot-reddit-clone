package com.example.springredditclone;

import org.testcontainers.containers.MySQLContainer;

public class BaseTest {

    static MySQLContainer mySQLContainer = (MySQLContainer) new MySQLContainer<>("mysql:latest")
            .withDatabaseName("spring-reddit-test-db")
            .withUsername("testuser")
            .withPassword("pass")
            .withReuse(true);

    static {
        mySQLContainer.start();
    }
}
