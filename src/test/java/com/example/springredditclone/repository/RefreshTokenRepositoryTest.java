package com.example.springredditclone.repository;

import com.example.springredditclone.model.RefreshToken;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class RefreshTokenRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Test
    void findByToken() {
        //Given
        RefreshToken expectedRefreshToken = new RefreshToken(123L, "3abce8dd", Instant.now());
        entityManager.merge(expectedRefreshToken);

        //When
        Optional<RefreshToken> actualRefreshToken = refreshTokenRepository.findByToken(expectedRefreshToken.getToken());

        //Then
        assertThat(actualRefreshToken).isPresent();
        assertThat(actualRefreshToken.get()).usingRecursiveComparison().isEqualTo(expectedRefreshToken);
    }

    @Test
    void deleteByToken() {
        //Given
        RefreshToken refreshToken1 = new RefreshToken(123L, "3abce8dd", Instant.now());
        entityManager.merge(refreshToken1);

        RefreshToken refreshToken2 = new RefreshToken(124L, "3blz5df", Instant.now());
        entityManager.merge(refreshToken2);

        //When
        refreshTokenRepository.deleteByToken(refreshToken1.getToken());
        Iterable<RefreshToken> acturalRefreshTokens = refreshTokenRepository.findAll();

        //Then
        assertThat(acturalRefreshTokens).hasSize(1).contains(refreshToken2);

    }
}