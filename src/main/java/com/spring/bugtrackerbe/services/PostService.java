package com.spring.bugtrackerbe.services;

import com.spring.bugtrackerbe.dto.PostResponseDTO;
import com.spring.bugtrackerbe.mappers.PostMapper;
import com.spring.bugtrackerbe.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Autowired
    public PostService(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    public List<PostResponseDTO> getPosts() {
        return this.postRepository.findAll()
                .stream()
                .map(this.postMapper::toResponse)
                .toList();
    }

    public List<PostResponseDTO> getPostsByProjectId(int projectId) {
        return this.postRepository.findByProjectId(projectId)
                .stream()
                .map(this.postMapper::toResponse)
                .toList();
    }
}
