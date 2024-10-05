# School Management Back

## Run Spring Boot application
```
mvn spring-boot:run
```

## Run following SQL insert statements
```
INSERT INTO roles(name) VALUES('ROLE_ADMINISTRATOR');
INSERT INTO roles(name) VALUES('ROLE_PROFESSOR');
INSERT INTO roles(name) VALUES('ROLE_STAFF');
INSERT INTO roles(name) VALUES('ROLE_STUDENT');
```

## Password generator using BCryptPasswordEncoder
```

If you need to directly generate a password, run the code in BCryptPasswordEncoderTest:
    
    @Test
    void generatePassword() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String[] password = {"MDP2norsys*"};
        for (String s : password) System.out.println(passwordEncoder.encode(s));
    }
    
This will give you a generated password like this:

$2a$10$VJn4VeSehsrkqpFqmFuwpur.3a7JOTQDjJwAW9tuTFUyUf9/kfKUu
```