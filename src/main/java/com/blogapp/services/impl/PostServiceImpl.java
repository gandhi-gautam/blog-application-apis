package com.blogapp.services.impl;

import com.blogapp.entities.Category;
import com.blogapp.entities.Post;
import com.blogapp.entities.User;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.PageResponse;
import com.blogapp.payloads.PostDto;
import com.blogapp.repositories.CategoryRepo;
import com.blogapp.repositories.PostRepo;
import com.blogapp.repositories.UserRepo;
import com.blogapp.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public PostDto createNewPost(PostDto postDto, int userId, int categoryId) {

        // get user
        User user = userRepo.findById(userId).orElseThrow(() -> new
                ResourceNotFoundException("User", "User ID", userId));
        Category category = categoryRepo.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

        Post post = dtoToPost(postDto);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        post = postRepo.save(post);
        PostDto createdPost = postToDto(post);
        return createdPost;
    }

    @Override
    public PostDto updatePost(PostDto postDto, int postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());

        Post updatedPost = postRepo.save(post);
        return postToDto(updatedPost);
    }

    @Override
    public PostDto getPostById(int postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        return postToDto(post);
    }

    @Override
    public PageResponse getAllPosts(int pageNumber, int pageSize, String fieldName, String sortDir) {
        Pageable pageable = generatePageRequestForAllPosts(pageSize, pageNumber, fieldName, sortDir);
        Page<Post> posts = postRepo.findAll(pageable);
        List<PostDto> postDtos = posts.getContent().stream().map((post) -> postToDto(post)).collect(Collectors.toList());
        PageResponse pageResponse = mapPostResponse(posts, postDtos);
        return pageResponse;
    }

    @Override
    public void deletePost(int postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
        postRepo.delete(post);
    }

    @Override
    public List<PostDto> getPostsByCategory(int categoryId) {
        Category category = categoryRepo.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
        List<Post> posts = postRepo.findByCategory(category);
        List<PostDto> postDtos = posts.stream().map((post) -> postToDto(post)).collect(Collectors.toList());
//        List<PostDto> postDtos = new ArrayList<>();
//        posts.forEach((post) -> {
//            PostDto postDto = postToDto(post);
//            postDtos.add(postDto);
//        });
        return postDtos;
    }

    @Override
    public List<PostDto> getPostsByUser(int userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
        List<Post> posts = postRepo.findByUser(user);
        List<PostDto> postDtos = posts.stream().map((post) -> postToDto(post)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> searchPost(String keyword) {
        List<Post> posts = postRepo.findByTitleContaining(keyword);
        List<PostDto> postDtos = posts.stream().map((post) -> postToDto(post)).collect(Collectors.toList());
        return postDtos;
    }

    private Post dtoToPost(PostDto postDto) {
        return mapper.map(postDto, Post.class);
    }

    private PostDto postToDto(Post post) {
        return mapper.map(post, PostDto.class);
    }

    private Pageable generatePageRequestForAllPosts(int pageSize, int pageNumber, String fieldName, String sortDir) {
        Sort sort = null;
        if (sortDir.equalsIgnoreCase("asc")) {
            sort = Sort.by(fieldName).ascending();
        } else {
            sort = Sort.by(fieldName).descending();
        }
        return PageRequest.of(pageNumber, pageSize, sort);
    }

    private PageResponse mapPostResponse(Page<Post> posts, List<PostDto> postDtos) {
        PageResponse pageResponse = new PageResponse();
        pageResponse.setContent(postDtos);
        pageResponse.setPageNumber(posts.getNumber());
        pageResponse.setPageSize(posts.getSize());
        pageResponse.setTotalElements(posts.getTotalElements());
        pageResponse.setTotalPages(posts.getTotalPages());
        pageResponse.setLastPage(posts.isLast());
        return pageResponse;
    }
}
