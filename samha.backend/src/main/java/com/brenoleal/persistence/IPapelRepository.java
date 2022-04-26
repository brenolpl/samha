package com.brenoleal.persistence;

import com.brenoleal.core.Papel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPapelRepository extends JpaRepository<Papel, Integer> {
    Papel findByNome(String nome);
    Papel findById(int id);
}
