package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Executable;
import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    MessageRepository messageRepository;

    @Override
    public void run(String... strings) throws Exception{
        roleRepository.save(new Role("USER"));
        roleRepository.save(new Role("ADMIN"));

        Role adminRole = roleRepository.findByRole("ADMIN");
        Role userRole = roleRepository.findByRole("USER");

        User user1 = new User("eyo@123.com", "eyob", "Eyob",
                "Amenu", true,
                "eyob");
        user1.setRoles(Arrays.asList(userRole));
        userRepository.save(user1);

        User user2 = new User("admin@admin.com", "admin", "Admin",
                "Admin", true,
                "admin");
        user2.setRoles(Arrays.asList(adminRole));
        userRepository.save(user2);

        Message message1 = new Message("COVID-19",
                "COVID-19 is caused by a corona virus called SARS-CoV-2.");
        message1.setUser(user2);
        messageRepository.save(message1);

        Message message2 = new Message("Update on COVID-19",
                "Older adults and people who have severe underlying medical conditions " +
                        "like heart or lung disease or diabetes seem to be at higher risk for developing " +
                        "more serious complications from COVID-19 illness.");
        message2.setUser(user1);
        messageRepository.save(message2);

    }
}
