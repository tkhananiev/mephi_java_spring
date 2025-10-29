package mephi_java_spring.booking.security;

public record JwtUserData(Long userId, String username, String role) {}
