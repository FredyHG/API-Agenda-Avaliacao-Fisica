package com.avaliacao.fisica.avp.utils;

import com.avaliacao.fisica.avp.model.ClienteModel;
import com.avaliacao.fisica.avp.requests.ClientePostRequest;
import com.avaliacao.fisica.avp.requests.ClientePutRequest;

import java.sql.Date;
import java.time.LocalDate;


public class ClienteCreator {

    public static ClienteModel createValidClient(){
        return ClienteModel.builder()
                .nome("Cliente_nome")
                .sobrenome("Cliente_sobrenome")
                .dataNascimento(Date.valueOf(LocalDate.now()))
                .idade(19L)
                .telefone("(37)020202020")
                .cpf("123.123.123-12")
                .build();

    }

    public static ClientePostRequest createValidClientPostRequest(){
        return ClientePostRequest.builder()
                .nome("Cliente_nome")
                .sobrenome("Cliente_sobrenome")
                .dataNascimento(LocalDate.now())
                .idade(19L)
                .telefone("(37)020202020")
                .cpf("123.123.123-12")
                .build();

    }

    public static ClientePutRequest createValidPutRequest(){
        return ClientePutRequest.builder()
                .id(1L)
                .nome("Cliente_nome_mod")
                .sobrenome("Cliente_sobrenome")
                .dataNascimento(LocalDate.now())
                .idade(19L)
                .telefone("(37)020202020")
                .cpf("73104520119")
                .build();
    }





}
