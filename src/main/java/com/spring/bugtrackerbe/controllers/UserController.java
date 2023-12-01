package com.spring.bugtrackerbe.controllers;

import com.spring.bugtrackerbe.dto.FilterUsersRequestDTO;
import com.spring.bugtrackerbe.dto.response.UserResponseDTO;
import com.spring.bugtrackerbe.exceptions.ResourcesNotFoundException;
import com.spring.bugtrackerbe.messages.CommonMessage;
import com.spring.bugtrackerbe.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<UserResponseDTO> filterUsers(
            @RequestBody @Valid FilterUsersRequestDTO filterUsersRequestDTO
    ) {
        return this.userService.filterUsers(filterUsersRequestDTO);
    }

    @PostMapping("/disable/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDTO disableUser(@PathVariable int id) {
        try {
            return this.userService.disableUserById(id);
        } catch (ResourcesNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, CommonMessage.UPDATE_FAILED + e.getMessage());
        }
    }

    @PostMapping("/enable/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDTO enableUser(@PathVariable int id) {
        try {
            return this.userService.enableUserById(id);
        } catch (ResourcesNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, CommonMessage.UPDATE_FAILED + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable int id) {
        try {
            this.userService.deleteUserById(id);
        } catch (ResourcesNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, CommonMessage.DELETE_FAILED + e.getMessage());
        }
    }
}
