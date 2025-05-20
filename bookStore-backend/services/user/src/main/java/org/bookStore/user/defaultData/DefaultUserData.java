package org.bookStore.user.defaultData;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bookStore.user.user.User;
import org.bookStore.user.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DefaultUserData implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void run(String... args) {

        if (userRepository.count() == 0) {

            List<User> users = List.of(
                    User.builder()
                            .fullName("Joana Ribeiro")
                            .username("joana")
                            .password("12345")
                            .email("joana@example.com")
                            .build(),

                    User.builder()
                            .fullName("Pedro Gomes")
                            .username("pedrog")
                            .password("12345")
                            .email("pedro@example.com")
                            .build()
            );

            userRepository.saveAll(users);

            System.out.println("Default users were created!");
        } else {
            System.out.println("Default users already exist!");
        }
    }
}
