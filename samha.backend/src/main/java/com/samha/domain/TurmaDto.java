package com.samha.domain;

import com.samha.persistence.filter.Query;
import lombok.Data;

@Data
public class TurmaDto {
    private Boolean ativas;
    private Boolean semestre;
    private String nome;
    private Query query;
}
