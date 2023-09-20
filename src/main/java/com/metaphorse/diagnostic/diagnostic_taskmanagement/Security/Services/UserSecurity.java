package com.metaphorse.diagnostic.diagnostic_taskmanagement.Security.Services;

import java.util.List;
import java.util.Set;

import com.metaphorse.diagnostic.diagnostic_taskmanagement.Model.User;
import com.metaphorse.diagnostic.diagnostic_taskmanagement.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurity implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUser(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new UserDetailsImpl(user);
    }

//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        var usuario = getById(username);
//
//        if (usuario == null) {
//            throw new UsernameNotFoundException(username);
//        }
//        return User
//                .withUsername(username)
//                .password(usuario.password())
//                .roles(usuario.roles().toArray(new String[0]))
//                .build();
//    }

    public record Usuario(String username, String password, Set<String> roles) {};

    public static Usuario getById(String username) {
        // "secreto" => [BCrypt] => "$2a$10$56VCAiApLO8NQYeOPiu2De/EBC5RWrTZvLl7uoeC3r7iXinRR1iiq"
        var password = "$2a$10$56VCAiApLO8NQYeOPiu2De/EBC5RWrTZvLl7uoeC3r7iXinRR1iiq";
        Usuario juan = new Usuario(
                "jcabelloc",
                password,
                Set.of("USER")
        );

        Usuario maria = new Usuario(
                "mlopez",
                password,
                Set.of("ADMIN")
        );
        var usuarios = List.of(juan, maria);

        return usuarios
                .stream()
                .filter(e -> e.username().equals(username))
                .findFirst()
                .orElse(null);
    }
}