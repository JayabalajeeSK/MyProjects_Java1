package com.jb.online_quiz_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jb.online_quiz_app.entity.User;
import com.jb.online_quiz_app.repository.UserRepository;
import com.jb.online_quiz_app.security.JwtTokenProvider;


@Service
public class AuthService 
{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;

    // Register user with plain-text password
    public String registerUser(String username, String password, String role) 
    {
        if (userRepository.findByUsername(username).isPresent()) 
        {
            return "Username already exists";
        }

        String encodedPassword = passwordEncoder.encode(password);

        User user = User.builder()
                        .username(username)
                        .password(encodedPassword)
                        .role(role)
                        .build();

        userRepository.save(user);
        return "User registered successfully";
    }

    // login user with plain-text password
    public String loginUser(String username, String password) 
    {
        try 
        {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            String token = jwtTokenProvider.generateToken(authentication);

            return "Bearer: " + token;

        } 
        catch (AuthenticationException ex) 
        {
            return "Invalid credentials";
        }
}
}