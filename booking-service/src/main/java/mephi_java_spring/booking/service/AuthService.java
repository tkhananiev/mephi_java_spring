package mephi_java_spring.booking.service;

import mephi_java_spring.booking.dto.AuthRequest;
import mephi_java_spring.booking.dto.AuthResponse;
import mephi_java_spring.booking.dto.RegisterRequest;
import mephi_java_spring.booking.entity.UserEntity;
import mephi_java_spring.booking.repository.UserRepository;
import mephi_java_spring.booking.security.JwtService;
import mephi_java_spring.booking.security.JwtUserData;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse register(RegisterRequest request) {
        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");

        userRepository.save(user);

        String token = jwtService.generateToken(user.getId(), user.getUsername(), user.getRole());

        AuthResponse resp = new AuthResponse();
        resp.setToken(token);
        resp.setRole(user.getRole());
        return resp;
    }

    public AuthResponse authenticate(AuthRequest request) {
        UserEntity user = userRepository.findByUsername(request.getUsername())
                .filter(u -> passwordEncoder.matches(request.getPassword(), u.getPassword()))
                .orElseThrow(() -> new RuntimeException("Bad credentials"));

        String token = jwtService.generateToken(user.getId(), user.getUsername(), user.getRole());

        AuthResponse resp = new AuthResponse();
        resp.setToken(token);
        resp.setRole(user.getRole());
        return resp;
    }

    public JwtUserData parse(String token) {
        return jwtService.parseToken(token);
    }
}
