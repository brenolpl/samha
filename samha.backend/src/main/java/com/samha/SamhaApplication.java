package com.samha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


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