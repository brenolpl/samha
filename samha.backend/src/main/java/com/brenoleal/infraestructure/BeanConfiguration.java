package com.brenoleal.infraestructure;

import com.brenoleal.commons.IUseCaseManager;
import com.brenoleal.commons.UseCaseFacade;
import com.brenoleal.util.GenerateAlphaNumericString;
import com.brenoleal.util.JWTSecret;
import com.google.gson.Gson;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class BeanConfiguration implements WebMvcConfigurer {

    @Bean
    public UseCaseFacade useCaseFacade(IUseCaseManager manager) {
        return new UseCaseFacade(manager);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner generateJWTSecret(){
        return args -> {
            String secret = GenerateAlphaNumericString.getRandomString(256);
            JWTSecret jwtSecret = new JWTSecret(secret);
            Gson gson = new Gson();
            // create a writer
            Writer writer = Files.newBufferedWriter(Paths.get("secret.json"));

            // convert user object to JSON file
            gson.toJson(jwtSecret, writer);

            // close writer
            writer.close();
        };
    }
}
