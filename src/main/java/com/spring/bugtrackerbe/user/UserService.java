package com.spring.bugtrackerbe.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
}
