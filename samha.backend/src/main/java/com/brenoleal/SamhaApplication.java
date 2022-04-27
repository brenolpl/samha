package com.brenoleal;

import com.brenoleal.core.Usuario;
import com.brenoleal.service.IUsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;


@SpringBootApplication
public class SamhaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SamhaApplication.class, args);
	}

//	@Bean
//	CommandLineRunner run(IUsuarioService usuarioService){
//		return args -> {
//			Usuario user = usuarioService.findByLogin("admin");
//			usuarioService.saveUsuario(user);
//		};
//	}
}
