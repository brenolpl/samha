package com.brenoleal.persistence;

import com.brenoleal.domain.Papel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPapelRepository extends JpaRepository<Papel, Integer> {
    Papel findByNome(String nome);
    Papel findById(int id);
}
