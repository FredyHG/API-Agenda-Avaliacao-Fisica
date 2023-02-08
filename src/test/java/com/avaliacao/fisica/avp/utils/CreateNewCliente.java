package com.avaliacao.fisica.avp.utils;

import com.avaliacao.fisica.avp.model.ClienteModel;
import com.avaliacao.fisica.avp.requests.ClientePostRequest;
import com.avaliacao.fisica.avp.requests.ClientePutRequest;
import org.springframework.data.domain.Page;

import java.sql.Date;
import java.time.LocalDate;


public class CreateNewCliente{

    public static ClienteModel createValidClient(){
        return ClienteModel.builder()
                .nome("Cliente_nome")
                .sobrenome("Cliente_sobrenome")
                .dataNascimento(Date.valueOf(LocalDate.now()))
                .idade(19L)
                .telefone("(37)020202020")
                .cpf("73104520119")
                .build();

    }

    public static ClientePostRequest createValidClientPostRequest(){
        return ClientePostRequest.builder()
                .nome("Cliente_nome")
                .sobrenome("Cliente_sobrenome")
                .dataNascimento(LocalDate.now())
                .idade(19L)
                .telefone("(37)020202020")
                .cpf("73104520119")
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
