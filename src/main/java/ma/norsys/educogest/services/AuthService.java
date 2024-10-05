package ma.norsys.educogest.services;

import ma.norsys.educogest.enumeration.ERole;
import ma.norsys.educogest.models.Role;
import ma.norsys.educogest.models.User;
import ma.norsys.educogest.payload.request.SignupRequest;
import ma.norsys.educogest.repository.RoleRepository;
import ma.norsys.educogest.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;


    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    public User registerUser(SignupRequest signUpRequest) {
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_STUDENT)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                Role _role = switch (role) {
                    case "admin" -> roleRepository.findByName(ERole.ROLE_ADMINISTRATOR)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    case "professor" -> roleRepository.findByName(ERole.ROLE_PROFESSOR)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    default -> roleRepository.findByName(ERole.ROLE_STUDENT)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                };
                roles.add(_role);
            });
        }

        user.setRoles(roles);
        return userRepository.save(user);
    }
}
