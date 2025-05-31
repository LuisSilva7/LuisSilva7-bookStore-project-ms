package org.bookStore.user.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bookStore.user.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@RequestBody @Valid RegisterRequest request) {
        RegisterResponse createdUser = authService.register(request);

        return ResponseEntity.ok(new ApiResponse<>(
                "User created successfully!", createdUser));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse token = authService.login(request);

        return ResponseEntity.ok(new ApiResponse<>(
                "User logged in successfully!", token));
    }
}


