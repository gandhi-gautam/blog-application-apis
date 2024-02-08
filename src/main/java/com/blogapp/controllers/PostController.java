package com.blogapp.controllers;

import com.blogapp.config.AppConstants;
import com.blogapp.payloads.ApiResponse;
import com.blogapp.payloads.PageResponse;
import com.blogapp.payloads.PostDto;
import com.blogapp.services.FileService;
import com.blogapp.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

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
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String fieldName,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.PAGE_DIR, required = false) String sortDir) {
        PageResponse pageResponse = postService.getAllPosts(pageNumber, pageSize, fieldName, sortDir);
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

    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<?> searchPosts(@PathVariable String keywords) {
        List<PostDto> postDtos = postService.searchPost("%" + keywords + "%");
        return ResponseEntity.ok(postDtos);
    }

    @PostMapping("/posts/image/upload/{postId}")
    public ResponseEntity<?> uploadPostImage(@PathVariable int postId, @RequestParam("image") MultipartFile image) throws IOException {
        PostDto postDto = postService.getPostById(postId);
        String fileName = fileService.uploadImage(path, image);
        postDto.setImageName(fileName);
        PostDto updatedPost = postService.updatePost(postDto, postId);
        return ResponseEntity.ok(updatedPost);
    }

    @GetMapping(value = "/posts/image/{imageName}", produces = MediaType.IMAGE_PNG_VALUE)
    public void downloadImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
        InputStream resource = fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
}
