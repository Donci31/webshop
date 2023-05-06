package hu.bme.aut.webshop.alf2023javant.controller;

import hu.bme.aut.webshop.alf2023javant.dto.LoginDto;
import hu.bme.aut.webshop.alf2023javant.dto.SignUpDto;
import hu.bme.aut.webshop.alf2023javant.entity.Role;
import hu.bme.aut.webshop.alf2023javant.entity.User;
import hu.bme.aut.webshop.alf2023javant.repository.UserRepository;
import hu.bme.aut.webshop.alf2023javant.security.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    @CrossOrigin
    public ResponseEntity<?> getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userRepository.findByEmail(username);
        return ResponseEntity.ok(user);
    }
}