package com.example.springredditclone.repository;

import com.example.springredditclone.model.Comment;
import com.example.springredditclone.model.Post;
import com.example.springredditclone.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class CommentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void shouldFindByPost() {
        //Given
        User user = new User(123L, "test user", "secret password", "user@email.com",
                Instant.now(), true);
        user = entityManager.merge(user);

        Post post = new Post(1234L, "First Post", "http://url.site", "Test",
                0, user, Instant.now(), null);
        post = entityManager.merge(post);

        Comment expectedComment = new Comment(123L, "Comment", post,Instant.now(), user);
        expectedComment = entityManager.merge(expectedComment);

        //When
        List<Comment> actualComment = commentRepository.findByPost(post);

        //Then
        assertThat(actualComment).hasSize(1);
        assertThat(actualComment).contains(expectedComment);
    }

    @Test
    void shouldFindAllByUser() {
        //Given
        User user = new User(123L, "test user", "secret password", "user@email.com",
                Instant.now(), true);
        user = entityManager.merge(user);

        Post post = new Post(1234L, "First Post", "http://url.site", "Test",
                0, user, Instant.now(), null);
        post = entityManager.merge(post);

        Comment expectedComment = new Comment(123L, "That's Comment", post,Instant.now(), user);
        expectedComment = entityManager.merge(expectedComment);

        //When
        List<Comment> actualComment = commentRepository.findAllByUser(user);

        //Then
        assertThat(actualComment).hasSize(1);
        assertThat(actualComment).contains(expectedComment);
    }
}