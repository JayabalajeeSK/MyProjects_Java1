package com.jb.online_quiz_app.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jb.online_quiz_app.service.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, String> req) 
    {
        String username = req.get("username");
        String password = req.get("password");
        String role = req.get("role"); // Example: "ADMIN" or "STUDENT"
        String response = authService.registerUser(username, password, role);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> req) 
    {
        String username = req.get("username");
        String password = req.get("password");
        String response = authService.loginUser(username, password);
        return ResponseEntity.ok(response);
    }
}
