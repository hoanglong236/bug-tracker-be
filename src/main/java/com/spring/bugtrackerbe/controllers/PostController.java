package com.spring.bugtrackerbe.controllers;

import com.spring.bugtrackerbe.dto.PostResponseDTO;
import com.spring.bugtrackerbe.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponseDTO> getPosts() {
        return this.postService.getPosts();
    }

    @GetMapping("/list-by-project/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponseDTO> getPostsByProjectId(@PathVariable int projectId) {
        return this.postService.getPostsByProjectId(projectId);
    }
}
