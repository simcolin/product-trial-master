package com.alten.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpiration;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/account")
    public String account(@RequestBody User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return "OK";
    }

    @PostMapping("/token")
    public ResponseEntity<String> token(@RequestBody LoginCredentials creds) {
        Optional<User> user = userRepository.findByEmail(creds.getEmail());
        if (user.isPresent()) {
            if (passwordEncoder.matches(creds.getPassword(), user.get().getPassword())) {
                String token = jwtUtil.generateToken(creds.getEmail());
                return ResponseEntity.ok(token);
            }
        }

        return ResponseEntity.badRequest().build();
    }
}
