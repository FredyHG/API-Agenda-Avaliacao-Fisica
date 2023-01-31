package com.avaliacao.fisica.avp.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientePutRequest {

    private Long id;

    private String nome;

    private String sobrenome;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dataNascimento;

    private Long idade;

    private String telefone;

    private String cpf;

}
