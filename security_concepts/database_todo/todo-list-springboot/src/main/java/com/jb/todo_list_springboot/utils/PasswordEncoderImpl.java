package com.jb.todo_list_springboot.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderImpl {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("user")); // $2a$10$13ojPYC/Y7s7qIt1EEddaujbEs/PA/PlwQmUU79q05zC0qFInV79q
        System.out.println(passwordEncoder.encode("admin")); // $2a$10$NIPMsnCDrsUO6EuQo9iaXe6Vf.c6pn9/ZZkOyWCDwp4Q1Ka69dY.y

    }

}