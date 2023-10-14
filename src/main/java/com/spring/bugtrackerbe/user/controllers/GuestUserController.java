package com.spring.bugtrackerbe.user.controllers;

import com.spring.bugtrackerbe.user.dtos.UserSignInRequestDTO;
import com.spring.bugtrackerbe.user.dtos.UserSignInResponseDTO;
import com.spring.bugtrackerbe.user.dtos.UserSignUpRequestDTO;
import com.spring.bugtrackerbe.user.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
        this.userService.userSignUp(signUpRequestDTO);
    }

    @PostMapping("/admin-sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void adminSignUp(@RequestBody @Valid UserSignUpRequestDTO signUpRequestDTO) {
        this.userService.adminSignUp(signUpRequestDTO);
    }

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserSignInResponseDTO signIn(@RequestBody @Valid UserSignInRequestDTO signInRequestDTO) {
        return this.userService.signIn(signInRequestDTO);
    }
}
