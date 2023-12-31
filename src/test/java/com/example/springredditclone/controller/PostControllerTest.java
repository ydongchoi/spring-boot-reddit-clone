package com.example.springredditclone.controller;

import com.example.springredditclone.dto.PostResponse;
import com.example.springredditclone.security.JwtProvider;
import com.example.springredditclone.service.PostService;
import com.example.springredditclone.service.UserDetailsServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Arrays.asList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = PostController.class)
public class PostControllerTest {

    @MockBean
    private PostService postService;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private JwtProvider jwtProvider;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should List All Posts When making GET request to endpoint - /api/posts/")
    public void shouldGetAllPosts() throws Exception {
        PostResponse postRequest1 = new PostResponse(1L, "Post Name", "http://url.site",
            "Description",
            "User 1", "Subreddit Name", 0, 0, "1 day ago");
        PostResponse postRequest2 = new PostResponse(2L, "Post Name2", "http://url2.site2",
            "Description2",
            "User 2", "Subreddit Name2", 0, 0, "2 day ago");

        Mockito.when(postService.getAllPosts(1, 3)).thenReturn(asList(postRequest1, postRequest2));

        mockMvc.perform(get("/api/posts/"))
            .andExpect(status().is(200))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.size", Matchers.is(2)))
            .andExpect(jsonPath("$[0].id", Matchers.is(1)))
            .andExpect(jsonPath("[0].postName", Matchers.is("Post Name")))
            .andExpect(jsonPath("$[0].url", Matchers.is("http://url.site")))
            .andExpect(jsonPath("$[1].url", Matchers.is("http://url2.site2")))
            .andExpect(jsonPath("$[1].postName", Matchers.is("Post Name 2")))
            .andExpect(jsonPath("$[1].id", Matchers.is(2)));
    }


}