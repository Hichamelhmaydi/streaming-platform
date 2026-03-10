package com.streaming.user.dto;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String email;
}