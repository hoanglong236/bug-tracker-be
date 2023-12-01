package com.spring.bugtrackerbe.dto.response;

import com.spring.bugtrackerbe.enums.UserRole;

import java.time.LocalDateTime;

public record UserResponseDTO(
        Integer id,
        String email,
        String name,
        UserRole role,
        Boolean enableFlag,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
