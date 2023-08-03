package com.hehehe.model.entity.dto;

import lombok.*;

import java.time.LocalDateTime;

public class UserDTO {

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class Request {

        private String userName;
        private String password;

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class Response {

        private Long userId;
        private String userName;

    }
}
