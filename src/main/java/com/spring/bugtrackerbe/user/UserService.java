package com.spring.bugtrackerbe.user;

import com.spring.bugtrackerbe.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}
