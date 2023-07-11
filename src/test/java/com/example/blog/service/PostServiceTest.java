package com.example.blog.service;

import com.example.blog.model.CreatePostRequest;
import com.example.blog.model.Post;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.domain.post.PostEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    PostRepository repository;

    PostService service;

    @Captor
    ArgumentCaptor<PostEntity> postEntityArgumentCaptor;

    private final String ID = "id";

    @BeforeEach
    void setUp() {
        service = new PostService(repository);
    }

    @Test
    void createPost() {
        when(repository.save(any())).thenReturn(getEntity());
        CreatePostRequest createPostRequest = new CreatePostRequest("userId", "content");

        service.createPost(createPostRequest);

        verify(repository).save(postEntityArgumentCaptor.capture());
        assertEquals(createPostRequest.getContent(), postEntityArgumentCaptor.getValue().getContent());
        assertEquals(createPostRequest.getUserId(), postEntityArgumentCaptor.getValue().getUserId());
    }

    @Test
    void findPostById() {
        when(repository.findById(any())).thenReturn(Optional.of(getEntity()));

        service.findPostById(ID);

        verify(repository).findById(eq(ID));
    }

    @Test
    void findPostByIdThrowsException() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> service.findPostById(ID));
        assertEquals("Post not found with id: " + ID, exception.getMessage());

        verify(repository).findById(eq(ID));
    }

    @Test
    void deletePostById() {
        PostEntity entity = getEntity();
        when(repository.findById(any())).thenReturn(Optional.of(entity));

        service.deletePostById(ID);

        verify(repository).findById(ID);
        verify(repository).delete(eq(entity));
    }

    @Test
    void deletePostByIdThrowsException() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> service.deletePostById(ID));
        assertEquals("Post not found with id: " + ID, exception.getMessage());

        verify(repository).findById(ID);
    }

    @Test
    void updatePostContent() {
        String newContent = "newContent";

        when(repository.findById(any())).thenReturn(Optional.of(getEntity()));

        service.updatePostContent(ID, newContent);

        verify(repository).findById(ID);
        verify(repository).save(postEntityArgumentCaptor.capture());
        assertEquals(newContent, postEntityArgumentCaptor.getValue().getContent());
    }

    @Test
    void getAllPosts() {
        PostEntity entityOld = getEntity();
        entityOld.setCreatedAt(LocalDateTime.MIN);
        PostEntity entityNew = getEntity();
        entityNew.setCreatedAt(LocalDateTime.MAX);

        when(repository.findAll()).thenReturn(List.of(entityOld, entityNew));

        List<Post> allPosts = service.getAllPosts();

        assertEquals(allPosts.get(0).getCreatedAt(), entityNew.getCreatedAt());
        assertEquals(allPosts.get(1).getCreatedAt(), entityOld.getCreatedAt());
    }

    private PostEntity getEntity() {
        PostEntity postEntity = new PostEntity();
        postEntity.setContent("content");
        postEntity.setId(ID);
        postEntity.setUserId("userId");
        postEntity.setCreatedAt(LocalDateTime.now());
        return postEntity;
    }
}