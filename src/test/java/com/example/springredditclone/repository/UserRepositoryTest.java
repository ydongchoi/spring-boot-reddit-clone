package com.example.springredditclone.repository;

import com.example.springredditclone.BaseTest;
import com.example.springredditclone.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest extends BaseTest {
    @Autowired
    private UserRepository userRepository;
    private static User expectedUserObject;

    @BeforeAll
    public static void setup(){
        expectedUserObject = new User(123L, "test user", "secret password", "user@email.com", Instant.now(), true);
    }

    @Test
    public void shouldSavePost(){
        User actualUserObject = userRepository.save(expectedUserObject);
        assertThat(actualUserObject).usingRecursiveComparison()
                .ignoringFields("userId").isEqualTo(expectedUserObject);
    }

    @Test
    public void shouldFindByUserName(){
        userRepository.save(expectedUserObject);
        Optional<User> actualUserObject = userRepository.findByUsername("test user");
        assertThat(actualUserObject.get()).usingRecursiveComparison()
                .ignoringFields("userId").isEqualTo(expectedUserObject);
    }
}