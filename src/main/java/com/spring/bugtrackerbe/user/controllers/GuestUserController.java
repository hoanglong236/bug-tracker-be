package com.spring.bugtrackerbe.user.controllers;

import com.spring.bugtrackerbe.common.CommonMessage;
import com.spring.bugtrackerbe.exceptions.ResourcesAlreadyExistsException;
import com.spring.bugtrackerbe.user.dtos.UserSignInRequestDTO;
import com.spring.bugtrackerbe.user.dtos.UserSignInResponseDTO;
import com.spring.bugtrackerbe.user.dtos.UserSignUpRequestDTO;
import com.spring.bugtrackerbe.user.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/guest")
public class GuestUserController {

    private final UserService userService;

    @Autowired
    public GuestUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user-sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void userSignUp(@RequestBody @Valid UserSignUpRequestDTO signUpRequestDTO) {
        try {
            this.userService.userSignUp(signUpRequestDTO);
        } catch (ResourcesAlreadyExistsException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, CommonMessage.CREATE_FAILED + e.getMessage());
        }
    }

    @PostMapping("/admin-sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void adminSignUp(@RequestBody @Valid UserSignUpRequestDTO signUpRequestDTO) {
        try {
            this.userService.adminSignUp(signUpRequestDTO);
        } catch (ResourcesAlreadyExistsException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, CommonMessage.CREATE_FAILED + e.getMessage());
        }
    }

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserSignInResponseDTO signIn(@RequestBody @Valid UserSignInRequestDTO signInRequestDTO) {
        return this.userService.signIn(signInRequestDTO);
    }
}
