package ma.norsys.educogest.controllers;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import ma.norsys.educogest.constants.Mappings;
import ma.norsys.educogest.dto.UserDTO;
import ma.norsys.educogest.models.User;
import ma.norsys.educogest.services.AuthService;
import ma.norsys.educogest.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import ma.norsys.educogest.payload.request.LoginRequest;
import ma.norsys.educogest.payload.request.SignupRequest;
import ma.norsys.educogest.payload.response.JwtResponse;
import ma.norsys.educogest.payload.response.MessageResponse;
import ma.norsys.educogest.security.jwt.JwtUtils;
import ma.norsys.educogest.security.config.UserDetailsImpl;

@RestController
@RequestMapping(value = Mappings.REQUEST_MAPPING_AUTH)
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final AuthService authService;
    private final JwtUtils jwtUtils;
    private final ModelMapper modelMapper;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, AuthService authService, JwtUtils jwtUtils, ModelMapper modelMapper) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.authService = authService;
        this.jwtUtils = jwtUtils;
        this.modelMapper = modelMapper;
    }


    @PostMapping(value = Mappings.SIGN_IN_PAGE)
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping(value = Mappings.SIGN_UP_PAGE)
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        return ResponseEntity.ok(getUserEntity(this.authService.registerUser(signUpRequest)));
    }

    private UserDTO getUserEntity(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
