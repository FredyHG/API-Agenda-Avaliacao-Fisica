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
public class AvaliacaoPostRequest {

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDate dataHora;

    private String alergias;

    private String limitacoesFisicas;

    private String cpf;


}
