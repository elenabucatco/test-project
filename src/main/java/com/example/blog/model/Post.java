package com.example.blog.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
public class Post {
    String id;
    String userId;
    String content;
    LocalDateTime createdAt;
}
