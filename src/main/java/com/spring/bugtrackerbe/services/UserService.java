package com.spring.bugtrackerbe.services;

import com.spring.bugtrackerbe.dto.request.FilterUsersRequestDTO;
import com.spring.bugtrackerbe.dto.response.UserResponseDTO;
import com.spring.bugtrackerbe.entities.User;
import com.spring.bugtrackerbe.exceptions.ResourcesNotFoundException;
import com.spring.bugtrackerbe.messages.CommonMessage;
import com.spring.bugtrackerbe.messages.UserMessage;
import com.spring.bugtrackerbe.repositories.UserJdbcRepository;
import com.spring.bugtrackerbe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserJdbcRepository userJdbcRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserJdbcRepository userJdbcRepository) {
        this.userRepository = userRepository;
        this.userJdbcRepository = userJdbcRepository;
    }

    private static UserResponseDTO toUserResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getRole(),
                user.getEnableFlag(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public Page<UserResponseDTO> filterUsers(FilterUsersRequestDTO filterRequestDTO)
            throws IllegalArgumentException {
        final List<String> acceptSortFields = List.of("id", "updated_at");
        if (!acceptSortFields.contains(filterRequestDTO.getSortField())) {
            throw new IllegalArgumentException(CommonMessage.SORT_FIELD_NOT_ACCEPTED);
        }
        return this.userJdbcRepository.filterUsers(filterRequestDTO);
    }

    public UserResponseDTO disableUserById(int id) {
        final User user = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException(UserMessage.NOT_FOUND));
        user.setEnableFlag(false);
        user.setUpdatedAt(LocalDateTime.now());

        final User updatedUser = this.userRepository.save(user);
        return toUserResponseDTO(updatedUser);
    }

    public UserResponseDTO enableUserById(int id) {
        final User user = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException(UserMessage.NOT_FOUND));
        user.setEnableFlag(true);
        user.setUpdatedAt(LocalDateTime.now());

        final User updatedUser = this.userRepository.save(user);
        return toUserResponseDTO(updatedUser);
    }

    public void deleteUserById(int id) {
        final User user = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException(UserMessage.NOT_FOUND));
        user.setDeleteFlag(true);
        user.setUpdatedAt(LocalDateTime.now());

        this.userRepository.save(user);
    }
}
