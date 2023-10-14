package com.spring.bugtrackerbe.user;

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

    @PostMapping("/login")
    public String login() {
        return "Ping";
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
}
