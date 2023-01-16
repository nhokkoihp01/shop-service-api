package nlu.shop.api.controllers;

import nlu.shop.api.dto.*;
import nlu.shop.api.exceptions.ErrorException;
import nlu.shop.api.exceptions.NotFoundException;
import nlu.shop.api.models.Role;
import nlu.shop.api.models.User;
import nlu.shop.api.repository.IRoleRepository;
import nlu.shop.api.repository.IUserRepository;
import nlu.shop.api.security.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private IUserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private IRoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JWTGenerator jwtGenerator;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, IUserRepository userRepository,
                          IRoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorException(HttpStatus.NOT_FOUND, "Username is already taken!"));
        }

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode((registerDto.getPassword())));

        Role roles = roleRepository.findByName("USER").get();
        user.setRoles(Collections.singletonList(roles));

        userRepository.save(user);

        return ResponseEntity
                .badRequest()
                .body(new ErrorException(HttpStatus.OK, "register success!"));
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto)  {
          Authentication authentication = authenticationManager.authenticate(
                  new UsernamePasswordAuthenticationToken(
                          loginDto.getUsername(),
                          loginDto.getPassword()));
          SecurityContextHolder.getContext().setAuthentication(authentication);
          String token = jwtGenerator.generateToken(authentication);
          return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);


    }
    @GetMapping("user/{id}")
    public ResponseEntity<Optional<User>> pokemonDetail(@PathVariable int id) {
        return ResponseEntity.ok(userRepository.findById(id));

    }

}
