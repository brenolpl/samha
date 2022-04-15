package com.brenoleal.persistence;

import com.brenoleal.core.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByLogin(String login);
}
