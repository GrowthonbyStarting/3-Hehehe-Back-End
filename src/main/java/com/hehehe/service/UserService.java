package com.hehehe.service;


import com.hehehe.model.entity.dto.UserDTO;

public interface UserService {

    void singUp(UserDTO.Request request);

    UserDTO.Response login(UserDTO.Request request);
}
