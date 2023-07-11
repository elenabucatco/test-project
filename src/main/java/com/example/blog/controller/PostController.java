package com.example.blog.controller;

import com.example.blog.model.CreatePostRequest;
import com.example.blog.model.Post;
import com.example.blog.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/posts")
@AllArgsConstructor
@Validated
public class PostController {
    private final PostService postService;

    @GetMapping("/{id}")
    Post getPostById(@PathVariable String id) {
       return postService.findPostById(id);
    }

    @GetMapping()
    List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @DeleteMapping("/{id}")
    void deletePostById(@PathVariable String id) {
        postService.deletePostById(id);
    }

    @PutMapping("/{id}")
    Post updatePostContent(@PathVariable String id, @Valid @NotBlank @RequestBody String content) {
        return postService.updatePostContent(id, content);
    }

    @PostMapping()
    Post createPost(@Valid @RequestBody CreatePostRequest request) {
        return postService.createPost(request);
    }
}
