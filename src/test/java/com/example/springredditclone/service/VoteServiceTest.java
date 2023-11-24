package com.example.springredditclone.service;

import com.example.springredditclone.dto.VoteDto;
import com.example.springredditclone.exception.SpringRedditException;
import com.example.springredditclone.model.Post;
import com.example.springredditclone.model.User;
import com.example.springredditclone.model.Vote;
import com.example.springredditclone.model.VoteType;
import com.example.springredditclone.repository.PostRepository;
import com.example.springredditclone.repository.VoteRepository;
import java.time.Instant;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VoteServiceTest {

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    AuthService authService;

    @Captor
    private ArgumentCaptor<Vote> voteArgumentCaptor;

    @Captor
    private ArgumentCaptor<Post> postArgumentCaptor;

    private VoteService voteService;

    @BeforeEach
    public void setup() {
        voteService = new VoteService(voteRepository, postRepository, authService);
    }

    @Test
    void shouldVoteWhenUpVoteSuccess() {
        //Given
        VoteDto voteDto = new VoteDto(VoteType.UPVOTE, 123L);
        User user = new User(12L, "test user", "secret password", "user@email.com",
            Instant.now(), true);
        Post post = new Post(123L, "First Post", "http://url.site", "Test",
            0, user, Instant.now(), null);
        Vote vote = new Vote(1234L, VoteType.DOWNVOTE, post, user);

        //When
        Mockito.when(postRepository.findById(123L)).thenReturn(Optional.of(post));
        Mockito.when(authService.getCurrentUser())
            .thenReturn(user);
        Mockito.when(voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, user))
            .thenReturn(Optional.of(vote));

        voteService.vote(voteDto);

        //Then
        Mockito.verify(voteRepository, Mockito.times(1)).save(voteArgumentCaptor.capture());
        Mockito.verify(postRepository, Mockito.times(1)).save(postArgumentCaptor.capture());

        Assertions.assertThat(voteArgumentCaptor.getValue().getVoteType())
            .isEqualTo(VoteType.UPVOTE);
        Assertions.assertThat(postArgumentCaptor.getValue().getVoteCount())
            .isEqualTo(1);
    }

    @Test
    public void shouldVoteWhenDownVoteSuccess() {
        //Given
        VoteDto voteDto = new VoteDto(VoteType.DOWNVOTE, 123L);
        User user = new User(12L, "test user", "secret password", "user@email.com",
            Instant.now(), true);
        Post post = new Post(123L, "First Post", "http://url.site", "Test",
            0, user, Instant.now(), null);
        Vote vote = new Vote(1234L, VoteType.UPVOTE, post, user);

        //When
        Mockito.when(postRepository.findById(123L)).thenReturn(Optional.of(post));
        Mockito.when(authService.getCurrentUser())
            .thenReturn(user);
        Mockito.when(voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, user))
            .thenReturn(Optional.of(vote));

        voteService.vote(voteDto);

        //Then
        Mockito.verify(voteRepository, Mockito.times(1)).save(voteArgumentCaptor.capture());
        Mockito.verify(postRepository, Mockito.times(1)).save(postArgumentCaptor.capture());

        Assertions.assertThat(voteArgumentCaptor.getValue().getVoteType())
            .isEqualTo(VoteType.DOWNVOTE);
        Assertions.assertThat(postArgumentCaptor.getValue().getVoteCount())
            .isEqualTo(-1);
    }

    @Test
    public void shouldThrowExceptionWhenVoteFails() {
        //Given
        VoteDto voteDto = new VoteDto(VoteType.DOWNVOTE, 123L);
        User user = new User(12L, "test user", "secret password", "user@email.com",
            Instant.now(), true);
        Post post = new Post(123L, "First Post", "http://url.site", "Test",
            0, user, Instant.now(), null);
        Vote vote = new Vote(1234L, VoteType.DOWNVOTE, post, user);

        //When
        Mockito.when(postRepository.findById(123L)).thenReturn(Optional.of(post));
        Mockito.when(authService.getCurrentUser())
            .thenReturn(user);
        Mockito.when(voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, user))
            .thenReturn(Optional.of(vote));

        //Then
        Assertions.assertThatThrownBy(() -> voteService.vote(voteDto))
            .isInstanceOf(SpringRedditException.class);
    }
}