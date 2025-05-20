package org.bookStore.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth/register")
    public ResponseEntity<?> registierUser(@RequestBody User user){

        if(userRepository.existsByUsername(user.getUsername())){

            return ResponseEntity.badRequest().body("Username already exists");

        }

        if(userRepository.existsByEmail(user.getEmail())){

            return ResponseEntity.badRequest().body("Email is already being used");

        }

        User newUser = new User();
        newUser.setFullname(user.getFullname());
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));


        userRepository.save(newUser);

        return ResponseEntity.ok(Map.of("message", "User created successfully"));

    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO){

        try {
            Authentication authentication =authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtils.generationJwtToken(authentication);

            return ResponseEntity.ok(Map.of("token", jwt));

        } catch (BadCredentialsException ex) {

            return ResponseEntity.status(401).body("Invalid username or password");

        }
    }


    @GetMapping("/user")
    public ResponseEntity<List<User>> getAllUser(@RequestBody User user){

        List<User> existUser = userRepository.findAll();


        return new ResponseEntity<>(existUser,HttpStatus.OK);

    }


    @GetMapping("/id/{username}")
    public ResponseEntity<Optional<User>> getUserIdByUsername(@PathVariable String username) {
        Optional<User> userId = userRepository.findByUsername(username);

        if (userId != null) {
            return ResponseEntity.ok(userId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
