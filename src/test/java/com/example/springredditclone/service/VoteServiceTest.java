package com.example.springredditclone.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.springredditclone.repository.PostRepository;
import com.example.springredditclone.repository.VoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VoteServiceTest {

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private PostRepository postRepository;

    @Mock AuthService authService;

    @Test
    void vote() {
    }
}