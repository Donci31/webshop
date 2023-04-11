package hu.bme.aut.webshop.alf2023javant.service;

import hu.bme.aut.webshop.alf2023javant.entity.MyUser;
import hu.bme.aut.webshop.alf2023javant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> optUser = userRepository.findByEmail(username);

        if (optUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found!");
        }

        MyUser user = optUser.get();

        return new User(
                user.getEmail(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()))
        );
    }

}
