package com.avaliacao.fisica.avp.utils;

import com.avaliacao.fisica.avp.model.AvaliacaoModel;
import com.avaliacao.fisica.avp.requests.AvaliacaoGetRequest;
import com.avaliacao.fisica.avp.requests.AvaliacaoPostRequest;
import com.avaliacao.fisica.avp.requests.AvaliacaoPutRequest;

import java.time.LocalDateTime;

public class AvaliacaoCreator {

    public static AvaliacaoModel createValidAvaliacao(){
        return AvaliacaoModel.builder()
                .alergias("Pelos")
                .dataHora(LocalDateTime.now().plusMinutes(5))
                .status(false)
                .clienteId(1L)
                .limitacoesFisicas("Dor no joelho")
                .build();
    }

    public static AvaliacaoPostRequest createValidAvaliacaoPostRequest(){
        return AvaliacaoPostRequest.builder()
                .dataHora(LocalDateTime.now().plusMinutes(5))
                .alergias("Pelos")
                .limitacoesFisicas("Dor no joelho")
                .clienteCpf("123.123.123-12")
                .build();
    }

    public static AvaliacaoGetRequest createValidAvaliacaoGetRequest(){
        return AvaliacaoGetRequest.builder()
                .alergias("Pelos")
                .dataHora(LocalDateTime.now().plusMinutes(5))
                .status(false)
                .cliente(ClienteCreator.createValidClient())
                .limitacoesFisicas("Dor no joelho")
                .build();
    }

    public static AvaliacaoPutRequest createValidAvaliacaoPutRequest(){
        return AvaliacaoPutRequest.builder()
                .dataHora(LocalDateTime.now().plusMinutes(5))
                .alergias("Cheiro verde")
                .limitacoesFisicas("Dor nas costas")
                .clienteCpf("123.123.123-12")
                .build();
    }

}
