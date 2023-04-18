package com.samha.service;


import com.samha.domain.Usuario;

import java.util.List;

public interface IUsuarioService {
    Usuario saveUsuario(Usuario usuario);
    Usuario findByLogin(String login);
    Usuario addPapelToUsuario(String login, String nomePapel);
    List<Usuario> findAll();
}
