//package vnpost.technology.controller;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import vnpost.technology.dto.ResponseDTO;
//import vnpost.technology.entity.LoginRequest;
//import vnpost.technology.entity.Role;
//import vnpost.technology.entity.User;
//import vnpost.technology.repo.UserRepo;
//import vnpost.technology.service.JwtTokenService;
//
//import javax.validation.Valid;
//import java.util.List;
//import java.util.Set;
//
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthAPIController {
//    @Autowired
//    UserRepo userRepo;
//
//    @Autowired
//    AuthenticationManager authenticationManager;
//    @Autowired
//    PasswordEncoder encoder;
//    @Autowired
//    JwtTokenService jwtTokenService;
//
//    @PostMapping("/signin")
//    public ResponseDTO<String> login(@Valid @RequestBody LoginRequest loginRequest) {
//        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
////      return ResponseEntity.ok(jwtTokenService.createToken(loginRequest.getUsername(),authentication.getAuthorities())); // trả về string Token
//        return ResponseDTO.<String>builder().code(String.valueOf(HttpStatus.OK.value())).data(jwtTokenService.createToken(loginRequest.getUsername(),authentication.getAuthorities())).build();
//    }
//    @PostMapping("/singup")
//    public ResponseEntity<?>  register(@Valid @RequestBody User userSignUp) {
//        User user = new User();
//        user.setUsername(userSignUp.getUsername());
//        user.setPassword(encoder.encode(userSignUp.getPassword()));
//        System.out.println(encoder.encode(userSignUp.getPassword()));
//        List<Role> strRoles = userSignUp.getRoles();
//
//        user.setRoles(strRoles);
//        userRepo.save(user);
//        return ResponseEntity.ok("User registered successfully!");
//    }
//}
