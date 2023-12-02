package com.spring.bugtrackerbe.controllers;

import com.spring.bugtrackerbe.dto.request.SignInRequestDTO;
import com.spring.bugtrackerbe.dto.request.SignUpRequestDTO;
import com.spring.bugtrackerbe.dto.response.SignInResponseDTO;
import com.spring.bugtrackerbe.exceptions.ResourcesAlreadyExistsException;
import com.spring.bugtrackerbe.messages.CommonMessage;
import com.spring.bugtrackerbe.services.GuestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/guest")
public class GuestController {

    private final GuestService guestService;

    @Autowired
    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @PostMapping("/user-sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void userSignUp(@RequestBody @Valid SignUpRequestDTO signUpRequestDTO) {
        try {
            this.guestService.userSignUp(signUpRequestDTO);
        } catch (ResourcesAlreadyExistsException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, CommonMessage.CREATE_FAILED + e.getMessage());
        }
    }

    @PostMapping("/admin-sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void adminSignUp(@RequestBody @Valid SignUpRequestDTO signUpRequestDTO) {
        try {
            this.guestService.adminSignUp(signUpRequestDTO);
        } catch (ResourcesAlreadyExistsException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, CommonMessage.CREATE_FAILED + e.getMessage());
        }
    }

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public SignInResponseDTO signIn(@RequestBody @Valid SignInRequestDTO signInRequestDTO) {
        return this.guestService.signIn(signInRequestDTO);
    }
}
