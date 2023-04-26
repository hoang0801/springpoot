package com.jmaster.io.shopservice.api;


import com.jmaster.io.shopservice.entity.LoginRequest;
import com.jmaster.io.shopservice.entity.Role;
import com.jmaster.io.shopservice.entity.User;
import com.jmaster.io.shopservice.model.CartDTO;
import com.jmaster.io.shopservice.model.ResponseDTO;
import com.jmaster.io.shopservice.repository.UserRepository;
import com.jmaster.io.shopservice.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;


@RestController
@RequestMapping("/api/auth")
public class AuthAPIController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtTokenService jwtTokenService;

    @PostMapping("/singin")
    public ResponseDTO<String> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//      return ResponseEntity.ok(jwtTokenService.createToken(loginRequest.getUsername(),authentication.getAuthorities())); // trả về string Token
        return ResponseDTO.<String>builder().code(String.valueOf(HttpStatus.OK.value())).data(jwtTokenService.createToken(loginRequest.getUsername(),authentication.getAuthorities())).build();
    }
    @PostMapping("/singup")
    public ResponseEntity<?>  register(@Valid @RequestBody User userSignUp) {
        User user = new User();
        user.setEmail(userSignUp.getEmail());
        user.setUsername(userSignUp.getUsername());
        user.setPassword(encoder.encode(userSignUp.getPassword()));
        System.out.println(encoder.encode(userSignUp.getPassword()));
        Set<Role> strRoles = userSignUp.getRoles();

        user.setRoles(strRoles);
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }
}
