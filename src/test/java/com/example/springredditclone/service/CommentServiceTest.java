package com.example.springredditclone.service;

import com.example.springredditclone.exception.SpringRedditException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CommentServiceTest {

    @Test
    @DisplayName("Test Should Pass When Comment do not Contains Swear Words")
    public void ShouldNotContainSwearWordsInsideComment() {
        //Isolationo of CommentService
        CommentService commentService = new CommentService(null, null,null,null,null,null,null);
        assertThat(commentService.containsSwearWords("This is a comment")).isFalse();
    }

    @Test
    @DisplayName("Should Throw Exception When Exception  Contains Swear Words")
    public void shouldFailWhenCommentContainsSwearWords(){
        CommentService commentService = new CommentService(null,null,null,null,null,null,null);

        assertThatThrownBy(() -> {
            commentService.containsSwearWords("This is a shitty comment");
        }).isInstanceOf(SpringRedditException.class)
                .hasMessage("Comments contains unacceptable language");
    }
}