package com.jumbo.store.controller.v1;

import com.jumbo.store.model.Role;
import com.jumbo.store.model.User;
import com.jumbo.store.payload.AuthenticationResponse;
import com.jumbo.store.payload.LoginRequest;
import com.jumbo.store.payload.SignUpRequest;
import com.jumbo.store.payload.UserResponse;
import com.jumbo.store.security.JwtTokenProvider;
import com.jumbo.store.service.RoleService;
import com.jumbo.store.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashSet;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Login API Methods")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final RoleService roleService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ModelMapper modelMapper;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmailOrUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signUp(
            @Valid @RequestBody SignUpRequest signUpRequest) {
        Role userRole = roleService.findByName("ROLE_USER");
        User user = modelMapper.map(signUpRequest, User.class);
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        user = userService.save(user);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        return ResponseEntity.ok(userResponse);
    }

}
