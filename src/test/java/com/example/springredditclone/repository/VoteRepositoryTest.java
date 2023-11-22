package com.example.springredditclone.repository;

import com.example.springredditclone.model.Post;
import com.example.springredditclone.model.User;
import com.example.springredditclone.model.Vote;
import com.example.springredditclone.model.VoteType;
import org.junit.jupiter.api.Test;
import org.mapstruct.control.MappingControl;
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
class VoteRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private VoteRepository voteRepository;

    @Test
    public void shouldFindTopByPostAndUserOrderByVoteIdDesc(){
        // Given
        User user = new User(123L, "test user", "secret password", "user@email.com", Instant.now(), true);
        user = entityManager.merge(user);

        Post post = new Post(1234L, "First Post", "http://url.site", "Test",
                0, user, Instant.now(), null);
        post = entityManager.merge(post);

        Vote expectedVote1 = new Vote(1L, VoteType.UPVOTE, post, user);
        expectedVote1 = entityManager.merge(expectedVote1);

        // When
        Optional<Vote> actualVote = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, user);

        // Then
        assertThat(actualVote).isPresent();
        assertThat(actualVote.get()).isEqualTo(expectedVote1);
    }

}