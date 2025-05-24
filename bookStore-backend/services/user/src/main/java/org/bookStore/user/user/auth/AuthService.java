package org.bookStore.user.user.auth;

import lombok.RequiredArgsConstructor;
import org.bookStore.user.user.cart.CartClient;
import org.bookStore.user.user.cart.CartRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final CartClient cartClient;

    public void register(User request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = User.builder().fullname(request.getFullname()).email(request.getEmail())
                .username(request.getUsername()).password(request.getPassword()).build();
        userRepository.save(user);

        System.out.println("ID DO USER CRIADO: " + user.getUserId());
        cartClient.createCart(new CartRequest(user.getUserId()));
    }

    public String login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Username not found"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getUserId());
    }
}


