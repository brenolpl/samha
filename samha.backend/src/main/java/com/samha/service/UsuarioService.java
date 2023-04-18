package com.samha.service;


import com.samha.domain.Papel;
import com.samha.domain.Usuario;
import com.samha.persistence.IPapelRepository;
import com.samha.persistence.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioService implements IUsuarioService{

    private final IUsuarioRepository usuarioRepository;
    private final IPapelRepository papelRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Usuario saveUsuario(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario findByLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }

    @Override
    public Usuario addPapelToUsuario(String login, String nomePapel) {
        Usuario usuario = usuarioRepository.findByLogin(login);
        Papel papel = papelRepository.findByNome(nomePapel);
        usuario.setPapel(papel);
        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }
}
