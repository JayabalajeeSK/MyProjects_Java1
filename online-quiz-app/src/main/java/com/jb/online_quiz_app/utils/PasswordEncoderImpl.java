package com.jb.online_quiz_app.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderImpl 
{
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("user")); //$2a$10$/grCbR8qHykIb0emm3czHeXY7qXNUL0Y1Vkd51SzKpDPuTe3ubPuu
        System.out.println(passwordEncoder.encode("admin")); //$2a$10$qSmhGJy1GbK0lxU9x..81uTTqooaF/tq85ymZWsfUKgX3kOlrUqaG
    }
}
