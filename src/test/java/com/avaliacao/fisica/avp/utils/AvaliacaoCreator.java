package com.avaliacao.fisica.avp.utils;

import com.avaliacao.fisica.avp.model.AvaliacaoModel;
import com.avaliacao.fisica.avp.requests.AvaliacaoPostRequest;

import java.sql.Date;
import java.time.LocalDate;

public class AvaliacaoCreator {

    public static AvaliacaoModel createValidAvaliacao(){
        return AvaliacaoModel.builder()
                .alergias("Pelos")
                .dataHora(Date.valueOf(LocalDate.now()))
                .status(false)
                .clientID(1L)
                .limitacoesFisicas("Dor no joelho")
                .build();
    }

    public static AvaliacaoPostRequest createValidAvaliacaoPostRequest(){
        return AvaliacaoPostRequest.builder()
                .dataHora(LocalDate.now())
                .alergias("Pelos")
                .limitacoesFisicas("Dor no joelho")
                .cpf("123")
                .build();

    }



}
