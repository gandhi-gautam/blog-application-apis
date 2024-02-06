package com.blogapp.controllers;

import com.blogapp.payloads.PostDto;
import com.blogapp.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<?> createPost(@RequestBody PostDto postDto, @PathVariable int userId,
                                        @PathVariable int categoryId) {
        PostDto createdPost = postService.createNewPost(postDto, userId, categoryId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<?> getAllPostsFromCategoryId(@PathVariable int categoryId) {
        List<PostDto> postDtos = postService.getPostsByCategory(categoryId);
        return ResponseEntity.ok(postDtos);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<?> getAllPostsFromUserId(@PathVariable int userId) {
        List<PostDto> postDtos = postService.getPostsByUser(userId);
        return ResponseEntity.ok(postDtos);
    }
}
