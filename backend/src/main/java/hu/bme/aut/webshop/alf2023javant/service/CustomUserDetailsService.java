package hu.bme.aut.webshop.alf2023javant.service;

import hu.bme.aut.webshop.alf2023javant.entity.User;
import hu.bme.aut.webshop.alf2023javant.repository.UserRepository;
import hu.bme.aut.webshop.alf2023javant.security.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optUser = userRepository.findByEmail(email);

        if (optUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found!");
        }

        User user = optUser.get();

        logger.info("User found: " + user.getEmail());
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().name())));
    }

}
