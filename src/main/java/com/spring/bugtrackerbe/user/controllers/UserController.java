package com.spring.bugtrackerbe.user.controllers;

import com.spring.bugtrackerbe.user.dtos.UserFilterRequestDTO;
import com.spring.bugtrackerbe.user.dtos.UserResponseDTO;
import com.spring.bugtrackerbe.user.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<UserResponseDTO> filterUsers(
            @RequestBody @Valid UserFilterRequestDTO filterRequestDTO) {

        return this.userService.filterUsers(filterRequestDTO);
    }
}
