package hu.bme.aut.webshop.alf2023javant.unit;

import hu.bme.aut.webshop.alf2023javant.entity.Role;
import hu.bme.aut.webshop.alf2023javant.entity.User;
import hu.bme.aut.webshop.alf2023javant.repository.UserRepository;
import hu.bme.aut.webshop.alf2023javant.service.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailServiceTest {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void testLoadUserByUsername_UserFound() {
        // Given
        String testEmail = "test@example.com";
        User testUser = new User();
        testUser.setEmail(testEmail);
        testUser.setPassword("testPassword");
        testUser.setRole(Role.USER);

        when(userRepository.findByEmail(testEmail)).thenReturn(Optional.of(testUser));

        // When
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(testEmail);

        // Then
        assertNotNull(userDetails);
        assertEquals(testEmail, userDetails.getUsername());
        assertEquals(testUser.getPassword(), userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals(testUser.getRole().name())));

        verify(userRepository, times(1)).findByEmail(testEmail);
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        // Given
        String testEmail = "test@example.com";

        when(userRepository.findByEmail(testEmail)).thenReturn(Optional.empty());

        // When
        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername(testEmail));

        // Then
        verify(userRepository, times(1)).findByEmail(testEmail);
    }
}

