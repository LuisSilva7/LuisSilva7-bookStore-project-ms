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
                            .fullname("Joana Ribeiro")
                            .email("joana@example.com")
                            .username("joana")
                            .password("123456")
                            .build(),

                    User.builder()
                            .fullname("Pedro Gomes")
                            .email("pedro@example.com")
                            .username("pedrog")
                            .password("123456")
                            .build(),

                    User.builder()
                            .fullname("Ana Martins")
                            .email("ana.martins@example.com")
                            .username("ana_m")
                            .password("123456")
                            .build(),

                    User.builder()
                            .fullname("Carlos Dias")
                            .email("carlos.d@example.com")
                            .username("carlosd")
                            .password("123456")
                            .build(),

                    User.builder()
                            .fullname("Rita Fernandes")
                            .email("rita.f@example.com")
                            .username("rita_fernandes")
                            .password("123456")
                            .build()
            );

            userRepository.saveAll(users);

            System.out.println("Default users were created!");
        } else {
            System.out.println("Default users already exist!");
        }
    }
}
