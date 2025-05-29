package com.jb.todo_list_springboot.service.impl;

import com.jb.todo_list_springboot.model.LoginDto;
import com.jb.todo_list_springboot.model.RegisterDto;

public interface AuthService {
    String register(RegisterDto registerDto);
    String login(LoginDto loginDto);

}
