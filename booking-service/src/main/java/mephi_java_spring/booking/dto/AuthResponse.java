package mephi_java_spring.booking.dto;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private String role;
}
