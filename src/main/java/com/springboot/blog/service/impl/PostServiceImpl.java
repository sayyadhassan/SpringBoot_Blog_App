package com.springboot.blog.service.impl;

import com.springboot.blog.dtos.PostDTO;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exceptions.ResourceNotFoundException;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    // convert Entity into DTO
    private PostDTO mapToDTO(Post post){
        PostDTO postDto = new PostDTO();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTiltle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }

    // convert DTO into Entity
    private Post mapToEntity(PostDTO postDTO){
        Post post = new Post();
        post.setTiltle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());
        return post;
    }

    @Override
    public PostDTO createPost(PostDTO postDto) {
        Post post = mapToEntity(postDto);

        Post newPost = postRepository.save(post);

        PostDTO postResponse = mapToDTO(newPost);

        return postResponse;
    }

    @Override
    public List<PostDTO> getAllPosts(int pazeNo, int pazeSize) {

        // create Pageable instance
        Pageable pageable = PageRequest.of(pazeNo,pazeSize);
        Page<Post> posts = postRepository.findAll(pageable);

        // get content from page object
        List<Post> listPost = posts.getContent();

        return listPost.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
    }

    @Override
    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("id","id",id));
        return mapToDTO(post);
    }

    @Override
    public PostDTO updatePost(PostDTO postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));

        post.setTiltle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePostById(Long id) {
//        postRepository.deleteById(id); // Pending why not this..?
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }


}
