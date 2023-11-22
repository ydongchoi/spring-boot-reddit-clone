package com.example.springredditclone.repository;

import com.example.springredditclone.BaseTest;
import com.example.springredditclone.model.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2) // Test 용 DB for protectecting real data
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 상용 DB
class PostRepositoryTest extends BaseTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    SubredditRepository subredditRepository;

    @Test
    @DisplayName("Should save one post")
    public void shouldSavePost() {
        Post expectedPostObjecct = new Post(null, "First Post", "http://url.site", "Test",
            0, null, Instant.now(), null);
        Post actualPostObject = postRepository.save(expectedPostObjecct);

        assertThat(actualPostObject).usingRecursiveComparison()
            .ignoringFields("postId").isEqualTo(expectedPostObjecct);
    }

    @Test
    public void shouldFindNoPostIfRepositorytIsEmpty() {
        Pageable paging = PageRequest.of(0, 3);
        Iterable postLsit = postRepository.findAll(paging);

        assertThat(postLsit).isEmpty();
    }

    @Test
    public void shouldFindAll() {
        Post expectedPostObject1 = new Post(null, "First Post", "http://url.site", "Test",
            0, null, Instant.now(), null);
        entityManager.persist(expectedPostObject1);

        Post expectedPostObject2 = new Post(null, "Second Post", "http://url.site", "Test",
            0, null, Instant.now(), null);
        entityManager.persist(expectedPostObject2);

        Post expectedPostObject3 = new Post(null, "Third Post", "http://url.site", "Test",
            0, null, Instant.now(), null);
        entityManager.persist(expectedPostObject3);

        Pageable paging = PageRequest.of(0, 3);
        Iterable<Post> actualPostList = postRepository.findAll(paging);

        assertThat(actualPostList).hasSize(3)
            .contains(expectedPostObject1, expectedPostObject2, expectedPostObject3);
    }
}