package com.spring.bugtrackerbe.services;

import com.spring.bugtrackerbe.dto.SignInRequestDTO;
import com.spring.bugtrackerbe.dto.response.SignInResponseDTO;
import com.spring.bugtrackerbe.dto.SignUpRequestDTO;
import com.spring.bugtrackerbe.entities.User;
import com.spring.bugtrackerbe.enums.UserRole;
import com.spring.bugtrackerbe.exceptions.ResourcesAlreadyExistsException;
import com.spring.bugtrackerbe.messages.UserMessage;
import com.spring.bugtrackerbe.repositories.UserRepository;
import com.spring.bugtrackerbe.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class GuestService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public GuestService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public void userSignUp(SignUpRequestDTO signUpRequestDTO) {
        this.signUp(signUpRequestDTO, UserRole.USER);
    }

    public void adminSignUp(SignUpRequestDTO signUpRequestDTO) {
        this.signUp(signUpRequestDTO, UserRole.ADMIN);
    }

    private void signUp(SignUpRequestDTO signUpRequestDTO, UserRole role) {
        if (this.userRepository.existsEmail(signUpRequestDTO.getEmail())) {
            throw new ResourcesAlreadyExistsException(UserMessage.EMAIL_ALREADY_EXISTS);
        }
        final User user = new User();
        user.setEmail(signUpRequestDTO.getEmail());
        user.setPassword(this.passwordEncoder.encode(signUpRequestDTO.getPassword()));
        user.setName(signUpRequestDTO.getName());
        user.setRole(role);

        this.userRepository.save(user);
    }

    public SignInResponseDTO signIn(SignInRequestDTO signInRequestDTO) {
        final Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequestDTO.getEmail(), signInRequestDTO.getPassword()));
        final UserDetails authUser = (UserDetails) authentication.getPrincipal();
        final String authorizeToken = this.jwtService.generateToken(authUser);
        return new SignInResponseDTO(authorizeToken);
    }
}
