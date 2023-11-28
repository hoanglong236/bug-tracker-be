package com.spring.bugtrackerbe.security;

import com.spring.bugtrackerbe.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SecurityRepository securityRepository;

    @Autowired
    public UserDetailsServiceImpl(SecurityRepository securityRepository) {
        this.securityRepository = securityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.securityRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(UserMessage.NOT_FOUND));
    }
}
