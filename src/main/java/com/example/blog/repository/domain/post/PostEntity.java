package com.example.blog.repository.domain.post;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String content;
    @Column(updatable = false)
    private String userId;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
