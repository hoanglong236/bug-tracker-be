package com.spring.bugtrackerbe.repositories;

import com.spring.bugtrackerbe.dto.request.FilterUsersRequestDTO;
import com.spring.bugtrackerbe.dto.response.UserResponseDTO;
import org.springframework.data.domain.Page;

public interface UserJdbcRepository {

    Page<UserResponseDTO> filterUsers(FilterUsersRequestDTO filterRequestDTO);
}
