package com.example.blog.model.mapper;

import com.example.blog.model.CreatePostRequest;
import com.example.blog.model.Post;
import com.example.blog.repository.domain.post.PostEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface PostMapper {
   PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);
   Post fromEntity(PostEntity entity);
   @Mapping(target = "id", ignore = true)
   @Mapping(target = "createdAt", ignore = true)
   PostEntity toEntity(CreatePostRequest post);
}
