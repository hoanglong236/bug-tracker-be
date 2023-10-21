package com.spring.bugtrackerbe.user.services;

import com.spring.bugtrackerbe.exceptions.ResourcesAlreadyExistsException;
import com.spring.bugtrackerbe.security.JwtService;
import com.spring.bugtrackerbe.user.dtos.*;
import com.spring.bugtrackerbe.user.entities.User;
import com.spring.bugtrackerbe.user.entities.UserRole;
import com.spring.bugtrackerbe.user.messages.UserMessage;
import com.spring.bugtrackerbe.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserService(
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

    public void userSignUp(UserSignUpRequestDTO signUpRequestDTO) {
        this.signUp(signUpRequestDTO, UserRole.USER);
    }

    public void adminSignUp(UserSignUpRequestDTO signUpRequestDTO) {
        this.signUp(signUpRequestDTO, UserRole.ADMIN);
    }

    private void signUp(UserSignUpRequestDTO signUpRequestDTO, UserRole role) {
        if (this.userRepository.findByEmail(signUpRequestDTO.getEmail()).isPresent()) {
            throw new ResourcesAlreadyExistsException(UserMessage.EMAIL_ALREADY_EXISTS);
        }
        final User user = new User();
        user.setEmail(signUpRequestDTO.getEmail());
        user.setPassword(this.passwordEncoder.encode(signUpRequestDTO.getPassword()));
        user.setName(signUpRequestDTO.getName());
        user.setRole(role);

        this.userRepository.save(user);
    }

    public UserSignInResponseDTO signIn(UserSignInRequestDTO signInRequestDTO) {
        final Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequestDTO.getEmail(), signInRequestDTO.getPassword()));
        final UserDetails authUser = (UserDetails) authentication.getPrincipal();
        final String authorizeToken = this.jwtService.generateToken(authUser);
        return new UserSignInResponseDTO(authorizeToken);
    }

    public List<UserResponseDTO> filterUsers(UserFilterRequestDTO filterRequestDTO) {
        final Pageable pageable = PageRequest.of(
                filterRequestDTO.getPageNumber(), filterRequestDTO.getPageSize());
        final Page<User> userPage = this.userRepository.findUsersWithRoleUser(pageable);

        return userPage.getContent()
                .stream()
                .map(this::userToUserResponseDTO)
                .toList();
    }

    private UserResponseDTO userToUserResponseDTO(User user) {
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
}
