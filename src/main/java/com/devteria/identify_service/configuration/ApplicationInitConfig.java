package com.devteria.identify_service.configuration;


import com.devteria.identify_service.Entity.UserEntity;
import com.devteria.identify_service.Enum.Role;
import com.devteria.identify_service.Repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
// bất cứ field nào khai báo bên trong nó tự make private, final
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j // sử dụng log
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    // đc khởi chạy mỗi khi app đc chạy lên
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                var roles = new HashSet<String>();
                roles.add(Role.ADMIN.name());
                UserEntity user = UserEntity.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
//                        .roles(roles)
                        .build();

                userRepository.save(user);
                log.warn("admin user has been created with default password: admin, please change it");
            }
        };
    }
}
