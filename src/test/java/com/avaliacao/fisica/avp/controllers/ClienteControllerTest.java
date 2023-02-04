package com.avaliacao.fisica.avp.controllers;

import com.avaliacao.fisica.avp.model.ClienteModel;
import com.avaliacao.fisica.avp.repositories.ClienteRepository;
import com.avaliacao.fisica.avp.services.ClienteService;
import com.avaliacao.fisica.avp.utils.CreateNewCliente;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Null;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
class ClienteControllerTest {

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ClienteService clienteServiceMock;


    @BeforeEach
    void setUp(){
        PageImpl<ClienteModel> clientePage = new PageImpl<>(List.of(CreateNewCliente.createValidClient()));
        BDDMockito.when(clienteServiceMock.findAllClientPageable(ArgumentMatchers.any()))
                .thenReturn(clientePage);
    }


    @Test
    @DisplayName("Should return a pageable list of 'Clientes'")
    public void should_return_a_pageable_list_of_Cliente(){
        String expectedName = CreateNewCliente.createValidClient().getNome();

        Page<ClienteModel> clientePage = clienteController.listAllClientes(null).getBody();

        Assertions.assertThat(clientePage).isNotNull();

        Assertions.assertThat(clientePage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(clientePage.toList().get(0).getNome()).isEqualTo(expectedName);
    }


}