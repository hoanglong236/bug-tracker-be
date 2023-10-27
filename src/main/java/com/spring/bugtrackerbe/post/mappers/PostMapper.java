package com.spring.bugtrackerbe.post.mappers;

import com.spring.bugtrackerbe.post.dto.PostRequestDTO;
import com.spring.bugtrackerbe.post.dto.PostResponseDTO;
import com.spring.bugtrackerbe.post.entities.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public Post toPost(PostRequestDTO requestDTO) {
        final Post post = new Post();

        post.setReporterId(requestDTO.getReporterId());
        post.setAssignerId(requestDTO.getAssignerId());
        post.setProjectId(requestDTO.getProjectId());
        post.setPhase(requestDTO.getPhase());
        post.setStatus(requestDTO.getStatus());
        post.setTitle(requestDTO.getTitle());
        post.setBugDesc(requestDTO.getBugDesc());
        post.setBugReason(requestDTO.getBugReason());
        post.setBugFixMethod(requestDTO.getBugFixMethod());

        return post;
    }

    public PostResponseDTO toResponse(Post post) {
        return new PostResponseDTO(
                post.getId(),
                post.getReporterId(),
                post.getAssignerId(),
                post.getProjectId(),
                post.getPhase(),
                post.getStatus(),
                post.getTitle(),
                post.getBugDesc(),
                post.getBugReason(),
                post.getBugFixMethod(),
                post.getDeleteFlag(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
