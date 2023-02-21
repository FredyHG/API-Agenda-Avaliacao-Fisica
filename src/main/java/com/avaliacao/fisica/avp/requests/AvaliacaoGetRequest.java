package com.avaliacao.fisica.avp.requests;

import com.avaliacao.fisica.avp.model.ClienteModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoGetRequest {

    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataHora;

    private boolean status;

    private String alergias;

    private String limitacoesFisicas;

    private ClienteModel cliente;

}
