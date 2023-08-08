package com.hehehe.controller;

import com.hehehe.model.entity.dto.UserDTO;
import com.hehehe.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin(origins = {"https://hehehe-eta.vercel.app/","http://localhost:3000/"})
//@CrossOrigin(origins = "*", allowCredentials = "true")
public class UserController {

    private final UserService userService;

    @PostMapping("/singUp")
    public void singUp(@RequestBody UserDTO.Request request) {
        userService.singUp(request);
    }

    @PostMapping("/login")
    public UserDTO.Response login(@RequestBody UserDTO.Request request) {
        return userService.login(request);
    }
}
