package main.security.controller;


import main.security.model.UserPrincipal;
import main.security.model.Users;
import main.security.service.MyUserDetailsService;
import main.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/security")
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    MyUserDetailsService userDetailsService;


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Users user) {
        System.out.println("register");
        if (userDetailsService.findByUsername(user.getUsername()) != null) {
            // Return with Content-Type: application/json
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .header("Content-Type", "application/json")
                    .body("{\"error\": \"Username already exists\"}");
        }
        Users registeredUser = service.register(user);

        // Return JSON response
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body("{\"id\": " + registeredUser.getId() + ", \"username\": \"" + registeredUser.getUsername() + "\"}");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users user) {
        System.out.println("login");
        return ResponseEntity.ok("{\"token\": \"" + service.verify(user)+ "\"}");
    }

    @PostMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        // Token w postaci "Bearer xyz"
        String jwt = token.substring(7);
        String username = service.extractUserName(jwt);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        try {
            if(service.validateToken(jwt, userDetails)){
                return ResponseEntity.ok().body("Token valid");
            } else{
                throw new Exception();
            }
        } catch (Exception e) {
            // Token niepoprawny, zwróć błąd
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }


    }

}
