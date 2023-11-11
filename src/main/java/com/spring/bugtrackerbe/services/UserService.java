package com.spring.bugtrackerbe.services;

import com.spring.bugtrackerbe.dto.FilterUsersRequestDTO;
import com.spring.bugtrackerbe.dto.UserResponseDTO;
import com.spring.bugtrackerbe.entities.User;
import com.spring.bugtrackerbe.exceptions.ResourcesNotFoundException;
import com.spring.bugtrackerbe.messages.UserMessage;
import com.spring.bugtrackerbe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    public Page<UserResponseDTO> filterUsers(FilterUsersRequestDTO filterUsersRequestDTO) {
        final Pageable pageable = PageRequest.of(
                filterUsersRequestDTO.getPageNumber(),
                filterUsersRequestDTO.getPageSize(),
                Sort.by(Sort.Direction.DESC, "id")
        );
        return this.userRepository.findUsersWithRoleUser(pageable)
                .map(UserService::toUserResponseDTO);
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
