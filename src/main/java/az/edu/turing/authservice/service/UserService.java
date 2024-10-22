package az.edu.turing.authservice.service;

import az.edu.turing.authservice.config.JwtUtil;
import az.edu.turing.authservice.domain.UserEntity;
import az.edu.turing.authservice.dto.AuthRequest;
import az.edu.turing.authservice.dto.AuthResponse;
import az.edu.turing.authservice.dto.UserDto;
import az.edu.turing.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private JwtUtil jwtUtil;

    private  AuthenticationManager authenticationManager;

    private  UserDetailsService userDetailsService;

    public UserEntity register(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new RuntimeException("User already exists");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword())); // Encode password

        return userRepository.save(userEntity);
    }


    public AuthResponse login(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());

        String token = jwtUtil.generateToken(String.valueOf(userDetails));

        return new AuthResponse(token);
    }

}
