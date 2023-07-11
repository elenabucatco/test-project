package com.example.blog.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreatePostRequest {
    @NotBlank(message = "User id must be present")
    private String userId;

    @NotBlank(message = "Content cannot be empty")
    private String content;

}
