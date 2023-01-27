package com.avaliacao.fisica.avp.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientePostRequest {

    private String nome;

    private String sobrenome;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dataNascimento;

    private Long idade;

    private String telefone;

    private String cpf;



}
