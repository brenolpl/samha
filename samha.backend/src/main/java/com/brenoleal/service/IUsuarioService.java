package com.brenoleal.service;

import com.brenoleal.core.Usuario;

import java.util.List;

public interface IUsuarioService {
    Usuario saveUsuario(Usuario usuario);
    Usuario findByLogin(String login);
    Usuario addPapelToUsuario(String login, String nomePapel);
    List<Usuario> findAll();
}
