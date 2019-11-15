package com.abhilash.jpa.controller;

import com.abhilash.jpa.model.Comment;
import com.abhilash.jpa.model.Post;
import com.abhilash.jpa.repository.CommentRepository;
import com.abhilash.jpa.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@RestController
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/posts/{postId}/comments")
    public Page<Comment> getAllCommentsByPostId(@PathVariable (value = "postId") Long postId,
                                                Pageable pageable) {
        return commentRepository.findByPostId(postId, pageable);
    }

    @PostMapping("/posts/{postId}/comments")
    public List<Comment> createComment(@PathVariable (value = "postId") Long postId,
                                       @Valid @RequestBody Set<Comment> comments) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("PostId " + postId + " not found"));
       comments.forEach(comment -> comment.setPost(post));
       return commentRepository.saveAll(comments);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable (value = "postId") Long postId,
                              @PathVariable (value = "commentId") Long commentId) {
        return commentRepository.findByIdAndPostId(commentId, postId).map(comment -> {
            commentRepository.delete(comment);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new NoSuchElementException("Comment not found with id " + commentId + " and postId " + postId));
    }
}
