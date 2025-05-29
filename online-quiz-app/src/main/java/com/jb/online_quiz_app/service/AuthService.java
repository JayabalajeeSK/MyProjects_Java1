package com.jb.online_quiz_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jb.online_quiz_app.entity.User;
import com.jb.online_quiz_app.repository.UserRepository;


@Service
public class AuthService 
{

    @Autowired
    private UserRepository userRepository;

    // Register user with plain-text password
    public String registerUser(String username, String password, String role) 
    {
        if (userRepository.findByUsername(username).isPresent()) 
        {
            return "Username already exists";
        }

        User user = User.builder()
                        .username(username)
                        .password(password) // ❗Plain-text (not secure for production)
                        .role(role)
                        .build();

        userRepository.save(user);
        return "User registered successfully";
    }

    // Login user by comparing plain-text password
    public String loginUser(String username, String password) 
    {
        return userRepository.findByUsername(username)
                             .map(user -> {
                                if (user.getPassword().equals(password)) 
                                {
                                    return "Login successful as " + user.getRole();
                                } 
                                else 
                                {
                                    return "Invalid credentials";
                                }
                            })
                            .orElse("User not found");
    }
}