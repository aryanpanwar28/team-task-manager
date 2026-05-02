package com.ttm.config;

import com.ttm.entity.User;
import com.ttm.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements ApplicationRunner {

    private final UserRepository users;
    private final PasswordEncoder encoder;

    public DataSeeder(UserRepository users, PasswordEncoder encoder) {
        this.users = users; this.encoder = encoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (users.count() == 0) {
            User u = User.builder()
                .name("Admin User")
                .email("admin@ttm.local")
                .password(encoder.encode("adminpass"))
                .role(User.Role.ADMIN)
                .build();
            users.save(u);
            System.out.println("[DataSeeder] Created initial admin user: admin@ttm.local / adminpass");
        } else {
            System.out.println("[DataSeeder] Users already present: skipping seeder");
        }
    }
}
