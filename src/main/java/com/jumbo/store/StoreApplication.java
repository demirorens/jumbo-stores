package com.jumbo.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumbo.store.model.Role;
import com.jumbo.store.model.Stores;
import com.jumbo.store.model.User;
import com.jumbo.store.security.JwtAuthenticationFilter;
import com.jumbo.store.service.JumboStoreService;
import com.jumbo.store.service.RoleService;
import com.jumbo.store.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@SpringBootApplication
@SecurityScheme(name = "bearerAuth",
        scheme = "bearer",
        bearerFormat = "JWT",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER)
@OpenAPIDefinition(info = @Info(title = "Jumbo Store API", version = "1.0",
        description = "This Api Has Login, Signup functions and Near function which returns nearest jumbo stores"))
public class StoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreApplication.class, args);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Initization of user, roles, and stores data to mongodb.
     *
     * @param jumboStoreService
     * @param userService
     * @param roleService
     * @return
     */


    @Bean
    CommandLineRunner initStoresAndTestUser(JumboStoreService jumboStoreService,
                                            UserService userService,
                                            RoleService roleService) {
        return args -> {
            //Create user role
            Role userRole = roleService.findByName("ROLE_USER");
            if (userRole == null) {
                userRole = new Role();
                userRole.setName("ROLE_USER");
                userRole = roleService.save(userRole);
            }
            //Create admin role
            Role adminRole = roleService.findByName("ROLE_ADMIN");
            if (adminRole == null) {
                adminRole = new Role();
                adminRole.setName("ROLE_ADMIN");
                adminRole = roleService.save(adminRole);
            }

            //Create admin user
            Optional<User> admin = userService.findByUsernameOrEmail("admin");
            User adminUser;
            if (admin.isEmpty()) {
                adminUser = new User();
                adminUser.setEmail("admin-user@gmail.com");
                adminUser.setUsername("admin");
                adminUser.setPassword("123456");
                adminUser.setFirstname("Admin");
                adminUser.setLastname("User");
                adminUser.setRoles(new HashSet<>(Arrays.asList(adminRole, userRole)));
                userService.save(adminUser);
            }

            //Create stores from file if don't exist
            long storeCount = jumboStoreService.getDocumentCount();
            if (storeCount < 587l) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    InputStream in = this.getClass().getClassLoader().getResourceAsStream("stores.json");
                    Stores stores = mapper.readValue(in.readAllBytes(), Stores.class);
                    jumboStoreService.insertAll(stores.getStores());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        };
    }

}
