package com.samha.domain;

import lombok.Data;

@Data
public class UsuarioDto {
    private Integer id;
    private String senha;
    private String login;
    private Integer papel_id;
    private Integer servidor_id;
}
