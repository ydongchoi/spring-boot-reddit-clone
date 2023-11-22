package com.example.springredditclone.repository;

import com.example.springredditclone.model.Post;
import com.example.springredditclone.model.Subreddit;
import com.example.springredditclone.model.User;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.Instant;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class SubredditRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SubredditRepository subredditRepository;

    @Test
    void findByName() {
        //Given
        User user = new User(123L, "test user", "secret password", "user@email.com", Instant.now(), true);
        user = entityManager.merge(user);

        Post post = new Post(1234L, "First Post", "http://url.site", "Test", 0, user, Instant.now(), null);
        post = entityManager.merge(post);

        List<Post> listPost = new ArrayList<>();
        listPost.add(post);

        Subreddit expectedSubreddit = new Subreddit(null, "Subreddit", "Description", listPost, Instant.now(), user);
        entityManager.merge(expectedSubreddit);

        //When
        Optional<Subreddit> actualSubreddit = subredditRepository.findByName("Subreddit");

        //Then
        assertThat(actualSubreddit).isPresent();
        // comparing fields of objects
        assertThat(actualSubreddit.get()).usingRecursiveComparison().isEqualTo(expectedSubreddit);
    }
}