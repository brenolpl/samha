package com.samha.persistence;

import com.samha.domain.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOfertaRepository extends JpaRepository<Oferta, Integer> {
}
