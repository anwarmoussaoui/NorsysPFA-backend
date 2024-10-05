package ma.norsys.educogest;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncoderTest {

    @Test
    void generatePassword() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String[] password = {"MDP2norsys*"};
        for (String s : password) System.out.println(passwordEncoder.encode(s));
    }

}