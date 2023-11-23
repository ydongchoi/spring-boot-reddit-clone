package com.example.springredditclone.repository;


import com.example.springredditclone.model.User;
import com.example.springredditclone.model.VerificationToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class VerificationTokenRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Test
    void findByToken() {
        //Given
        User user = new User(123L, "test user", "secret password", "user@email.com", Instant.now(), true);
        entityManager.merge(user);
        VerificationToken expectedVerificationToken = new VerificationToken(123L, "5sd2fkj8ed", user, Instant.now());
        entityManager.merge(expectedVerificationToken);

        //When
        Optional<VerificationToken> actualVerficationToken = verificationTokenRepository.findByToken(expectedVerificationToken.getToken());

        //Then
        assertThat(actualVerficationToken).isPresent();
        assertThat(actualVerficationToken.get()).usingRecursiveComparison().isEqualTo(expectedVerificationToken);
    }
}