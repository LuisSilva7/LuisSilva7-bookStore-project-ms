package org.bookStore.user.auth;

import lombok.RequiredArgsConstructor;
import org.bookStore.user.exception.custom.InvalidCredentialsException;
import org.bookStore.user.exception.custom.UserAlreadyExistsException;
import org.bookStore.user.exception.custom.UserNotFoundException;
import org.bookStore.user.user.User;
import org.bookStore.user.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists with username: " + request.username());
        }

        User user = User.builder().fullname(request.fullname())
                .email(request.email())
                .username(request.username())
                .password(request.password())
                .build();
        userRepository.save(user);

        return new RegisterResponse(
                user.getId(),
                user.getFullname(),
                user.getEmail(),
                user.getUsername()
        );
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + request.getUsername()));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials!");
        }

        String token = jwtUtil.generateToken(user.getId());

        return new LoginResponse(token);
    }
}


