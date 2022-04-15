package com.brenoleal.security;

import com.brenoleal.core.CoordenadorAcademico_;
import com.brenoleal.core.Usuario;
import com.brenoleal.core.Usuario_;
import com.brenoleal.persistence.IGenericRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class SamhaUserDetailsService implements UserDetailsService {

    @Inject
    private IGenericRepository genericRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = this.genericRepository.findSingle(Usuario.class, q -> q.where(
            q.equal(q.get(Usuario_.login), username)
        ));
        if(usuario == null){
            throw new UsernameNotFoundException("Usuario não encontrado");
        }else{
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            usuario.getPapeis().forEach( papel -> authorities.add(new SimpleGrantedAuthority(papel.getNome())));
            return new User(usuario.getLogin(), usuario.getSenha(), authorities);
        }
    }
}
