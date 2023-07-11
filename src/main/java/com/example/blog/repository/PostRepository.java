package com.example.blog.repository;

import com.example.blog.repository.domain.post.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, String>{
    List<PostEntity> findAll();
    Optional<PostEntity> findById(String id);
    void deleteById(String id);
}
