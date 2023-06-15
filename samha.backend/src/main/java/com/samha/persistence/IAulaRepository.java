package com.samha.persistence;

import com.samha.domain.Aula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAulaRepository extends JpaRepository<Aula, Integer> {
    List<Aula> getAulasByOferta_Id(Integer ofertaId);
}
