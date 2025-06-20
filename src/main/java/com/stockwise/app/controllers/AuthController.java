package com.stockwise.app.controllers;

import com.stockwise.app.dto.LoginDto;
import com.stockwise.app.dto.AuthResponseDto;
import com.stockwise.app.model.UserModel;
import com.stockwise.app.security.CustomUserDetails;
import com.stockwise.app.security.jwt.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/encode")
    public String encodePassword(@RequestParam String raw) {
        return passwordEncoder.encode(raw);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getSenha()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Cast correto:
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UserModel user = userDetails.getUserModel();

        String token = jwtTokenProvider.generateToken(user);

        return ResponseEntity.ok(new AuthResponseDto(token));
    }
}
