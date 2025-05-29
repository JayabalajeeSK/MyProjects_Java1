package com.jb.todo_list_springboot.service;

import java.util.HashSet;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.jb.todo_list_springboot.model.LoginDto;
import com.jb.todo_list_springboot.model.RegisterDto;
import com.jb.todo_list_springboot.model.Role;
import com.jb.todo_list_springboot.model.User;
import com.jb.todo_list_springboot.repo.RoleRepository;
import com.jb.todo_list_springboot.repo.UserRepository;
import com.jb.todo_list_springboot.service.impl.AuthService;

@Service
public class AuthServiceImpl  implements AuthService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public String register(RegisterDto registerDto)
    {

        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        User user = new User();
        
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER");
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        return "User Register Successfully";
            
    }

    @Override
    public String login(LoginDto loginDto) 
    {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "User Logged In Successfully";
    }

// @Override
// public String login(LoginDto loginDto) {
//     User user = userRepository.findByUsername(loginDto.getUsername())
//         .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

//     if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
//         throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
//     }

//     // If using JWT, generate and return token here.
//     return "Login Successful for user: " + user.getUsername();
// }


}
