package mephi_java_spring.booking.controller;

import mephi_java_spring.booking.dto.AuthRequest;
import mephi_java_spring.booking.dto.AuthResponse;
import mephi_java_spring.booking.dto.RegisterRequest;
import mephi_java_spring.booking.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/user/register")
    public AuthResponse register(@RequestBody @Valid RegisterRequest req) {
        return authService.register(req);
    }

    @PostMapping("/user/auth")
    public AuthResponse auth(@RequestBody @Valid AuthRequest req) {
        return authService.authenticate(req);
    }
}
