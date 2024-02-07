package com.blogapp.controllers;

import com.blogapp.payloads.ApiResponse;
import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.PageResponse;
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

    @GetMapping("/posts/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable int postId) {
        PostDto postDto = postService.getPostById(postId);
        return ResponseEntity.ok(postDto);
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        PageResponse pageResponse = postService.getAllPosts(pageNumber, pageSize);
        return ResponseEntity.ok(pageResponse);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<?> deletePostById(@PathVariable int postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Post Deleted Successfully", true), HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<?> updatePost(@RequestBody PostDto postDto, @PathVariable int postId) {
        PostDto updatedPost = postService.updatePost(postDto, postId);
        return ResponseEntity.ok(updatedPost);
    }
}
