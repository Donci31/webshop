package hu.bme.aut.webshop.alf2023javant.controller;

import hu.bme.aut.webshop.alf2023javant.dto.LoginDto;
import hu.bme.aut.webshop.alf2023javant.entity.User;
import hu.bme.aut.webshop.alf2023javant.entity.Role;
import hu.bme.aut.webshop.alf2023javant.dto.SignUpDto;
import hu.bme.aut.webshop.alf2023javant.repository.UserRepository;
import hu.bme.aut.webshop.alf2023javant.security.JwtUtils;

import hu.bme.aut.webshop.alf2023javant.service.CustomUserDetailsService;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public class ResponseTransferObject {
        private String message;
        private String token;

        public ResponseTransferObject(String message) {
            this.message = message;
            this.token = null;
            logger.info("ResponseTransferObject created: " + message);
        }

        public ResponseTransferObject(String message, String token) {
            this.message = message;
            this.token = token;
            logger.info("ResponseTransferObject created: " + message + " " + token);
        }

        public String getMessage() {
            return message;
        }

        public String getToken() {
            return token;
        }
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    @CrossOrigin
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto loginDto) {

        if (!userRepository.existsByEmail(loginDto.getEmail())) {
            logger.info("No such user!");
            return new ResponseEntity<ResponseTransferObject>(new ResponseTransferObject("No such user!"),
                    HttpStatus.UNAUTHORIZED);
        }
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(), loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            logger.info("User logged in successfully!");
            return ResponseEntity.ok(new ResponseTransferObject("User logged in successfully!", jwt));
        } catch (BadCredentialsException e) {
            logger.info("Incorrect password!");
            return new ResponseEntity<ResponseTransferObject>(
                    new ResponseTransferObject("Incorrect password!"),
                    HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            logger.info("Something went wrong!");
            return new ResponseEntity<ResponseTransferObject>(
                    new ResponseTransferObject("Something went wrong!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {

        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            logger.info("Email is already taken!");
            return new ResponseEntity<>(new ResponseTransferObject("Email is already taken!"), HttpStatus.CONFLICT);
        }

        User user = new User();
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);

        logger.info("User registered successfully!");
        return new ResponseEntity<>(new ResponseTransferObject("User registered successfully!"), HttpStatus.OK);
    }
}