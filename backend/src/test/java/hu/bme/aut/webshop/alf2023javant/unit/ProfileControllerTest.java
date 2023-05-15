package hu.bme.aut.webshop.alf2023javant.unit;

import hu.bme.aut.webshop.alf2023javant.controller.ProfileController;
import hu.bme.aut.webshop.alf2023javant.entity.User;
import hu.bme.aut.webshop.alf2023javant.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ProfileControllerTest {

    @InjectMocks
    private ProfileController profileController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetProfileSuccess() {
        User user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@example.com");

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

        Authentication auth = new TestingAuthenticationToken(user.getEmail(), null);
        SecurityContextHolder.getContext().setAuthentication(auth);

        ResponseEntity<?> response = profileController.getProfile();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetProfileUserNotFound() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        Authentication auth = new TestingAuthenticationToken("nonexistent@example.com", null);
        SecurityContextHolder.getContext().setAuthentication(auth);

        assertThrows(NoSuchElementException.class, () -> {
            profileController.getProfile();
        });
    }


}
