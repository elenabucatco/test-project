package com.example.blog.service;

import com.example.blog.model.CreatePostRequest;
import com.example.blog.model.Post;
import com.example.blog.model.mapper.PostMapper;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.domain.post.PostEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;

    private final PostMapper mapper = PostMapper.INSTANCE;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post createPost(CreatePostRequest request) {
        PostEntity postEntity = mapper.toEntity(request);
        return mapper.fromEntity(
                postRepository.save(postEntity)
        );
    }

    public Post findPostById(String id) {
        Optional<PostEntity> optionalPostEntity = postRepository.findById(id);

        return optionalPostEntity
                .map(mapper::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));
    }

    public void deletePostById(String id) {
        Optional<PostEntity> entity = postRepository.findById(id);
        postRepository.delete(entity.orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id)));
    }

    public Post updatePostContent(String postId, String newContent) {
        Optional<PostEntity> entity = postRepository.findById(postId);
        return entity.map(e -> {
            e.setContent(newContent);
            postRepository.save(e);
            return mapper.fromEntity(e);
        }).orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + postId));
    }

    public List<Post> getAllPosts() {
        List<PostEntity> all = postRepository.findAll();
        return all.stream()
                .sorted(Comparator.comparing(PostEntity::getCreatedAt).reversed())
                .map(mapper::fromEntity)
                .collect(Collectors.toList());
    }

}
