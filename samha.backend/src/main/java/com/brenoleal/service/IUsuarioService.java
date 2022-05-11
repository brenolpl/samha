package com.brenoleal.service;


import com.brenoleal.domain.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IUsuarioService {
    Usuario saveUsuario(Usuario usuario);
    Usuario findByLogin(String login);
    Usuario addPapelToUsuario(String login, String nomePapel);
    List<Usuario> findAll();
}
