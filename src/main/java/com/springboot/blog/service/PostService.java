package com.springboot.blog.service;

import com.springboot.blog.dtos.PostDTO;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDto);

    List<PostDTO> getAllPosts(int pazeNo, int pazeSize);

    PostDTO getPostById(Long id);

    PostDTO updatePost(PostDTO postDto, long id);

    void deletePostById(Long id);
}
