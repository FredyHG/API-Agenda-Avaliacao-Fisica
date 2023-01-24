package com.avaliacao.fisica.avp.utils;

import com.avaliacao.fisica.avp.model.ClienteModel;

import java.util.Date;

public class CreateNewCliente{

    public static ClienteModel createValidClient(){
        return ClienteModel.builder()
                .nome("Cliente_nome")
                .sobrenome("Cliente_sobrenome")
                .dataNascimento(new Date())
                .idade(19L)
                .telefone("(37)020202020")
                .cpf("73104520119")
                .build();

    }


}
