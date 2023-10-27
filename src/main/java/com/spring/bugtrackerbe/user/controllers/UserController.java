package com.spring.bugtrackerbe.user.controllers;

import com.spring.bugtrackerbe.common.CommonMessage;
import com.spring.bugtrackerbe.exceptions.ResourcesNotFoundException;
import com.spring.bugtrackerbe.user.dto.UserFilterRequestDTO;
import com.spring.bugtrackerbe.user.dto.UserResponseDTO;
import com.spring.bugtrackerbe.user.services.UserService;
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
            @RequestBody @Valid UserFilterRequestDTO filterRequestDTO) {

        return this.userService.filterUsers(filterRequestDTO);
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
