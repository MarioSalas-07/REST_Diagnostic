package com.metaphorse.diagnostic.diagnostic_taskmanagement.Controller;

import java.util.HashMap;
import java.util.Map;

import com.metaphorse.diagnostic.diagnostic_taskmanagement.Model.TokenInfo;
import com.metaphorse.diagnostic.diagnostic_taskmanagement.Model.User;
import com.metaphorse.diagnostic.diagnostic_taskmanagement.Security.Services.JwtUtilService;
import com.metaphorse.diagnostic.diagnostic_taskmanagement.Security.Services.UserSecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class TokenController {
    @Autowired
    private AuthenticationManager authenticationManager;

//    @Autowired
//    UserDetailsService usuarioDetailsService;
    @Autowired
    UserSecurity usuarioDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;
    private static final Logger logger = LoggerFactory.getLogger(TokenController.class);

    @GetMapping("/mensaje")
    public ResponseEntity<?> getMensaje() {
        logger.info("Obteniendo el mensaje");

        var auth =  SecurityContextHolder.getContext().getAuthentication();
        logger.info("Datos del Usuario: {}", auth.getPrincipal());
        logger.info("Datos de los Roles {}", auth.getAuthorities());
        logger.info("Esta autenticado {}", auth.isAuthenticated());

        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("contenido", "Hola Peru");
        return ResponseEntity.ok(mensaje);
    }

    @GetMapping("/admin")
    public ResponseEntity<?> getMensajeAdmin() {

        var auth =  SecurityContextHolder.getContext().getAuthentication();
        logger.info("Datos del Usuario: {}", auth.getPrincipal());
        logger.info("Datos de los Permisos {}", auth.getAuthorities());
        logger.info("Esta autenticado {}", auth.isAuthenticated());

        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("contenido", "Hola Admin");


        return ResponseEntity.ok(mensaje);
    }

    @GetMapping("/publico")
    public ResponseEntity<?> getMensajePublico() {
        var auth =  SecurityContextHolder.getContext().getAuthentication();
        logger.info("Datos del Usuario: {}", auth.getPrincipal());
        logger.info("Datos de los Permisos {}", auth.getAuthorities());
        logger.info("Esta autenticado {}", auth.isAuthenticated());

        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("contenido", "Hola. esto es publico");
        return ResponseEntity.ok(mensaje);
    }



    @PostMapping("/publico/authenticate")
    public ResponseEntity<TokenInfo> authenticate(@RequestBody User authenticationReq) {
        logger.info("Autenticando al usuario {}", authenticationReq.getUser());
        logger.info("getUsername {}", authenticationReq.getUser());
        logger.info("getPassword {}", authenticationReq.getPass());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationReq.getUser(),
                            authenticationReq.getPass()));

            UserDetails userDetails = usuarioDetailsService.loadUserByUsername(
                    authenticationReq.getUser());

            String jwt = jwtUtilService.generateJwtToken(authentication);

            return ResponseEntity.ok(new TokenInfo(jwt));
        } catch (AuthenticationException e) {

            logger.info("Autenticando al usuario {}", authenticationReq.getUser() + "Autenticando al password" +authenticationReq.getPass());
            logger.info(e.getMessage());
            return null;
        }
    }
}
