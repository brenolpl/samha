package com.brenoleal.infraestructure;

import com.brenoleal.commons.IUseCaseManager;
import com.brenoleal.commons.UseCaseFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
}
