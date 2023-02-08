package com.avaliacao.fisica.avp.controllers;

import com.avaliacao.fisica.avp.model.ClienteModel;
import com.avaliacao.fisica.avp.requests.ClientePostRequest;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

        BDDMockito.when(clienteServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(CreateNewCliente.createValidClient()));

        BDDMockito.when(clienteServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(CreateNewCliente.createValidClient()));

        BDDMockito.when(clienteServiceMock.findByNomeAndSobrenome(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
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

    @Test
    public void should_return_a_edited_Cliente(){

        Assertions.assertThatCode(() -> clienteServiceMock.replace(CreateNewCliente.createValidPutRequest()))
                .doesNotThrowAnyException();

    }

    @Test
    @DisplayName("Should return a 'Cliente' by ID")
    public void should_return_a_Client_by_ID() {
        ClienteModel expectedCliente = CreateNewCliente.createValidClient();

        Object cliente = clienteController.findByIdNOn(1).getBody();

        Assertions.assertThat(cliente).isNotNull().isEqualTo(expectedCliente);
    }

    @Test
    @DisplayName("Should return a 'Cliente' by NomeAndSobrenome")
    public void should_return_a_Client_by_nome_and_sobrenome() {
        PageImpl<ClienteModel> clientePage = new PageImpl<>(List.of(CreateNewCliente.createValidClient()));

        Object body = clienteController.findByName("test", "test").getBody();


        Assertions.assertThat(Objects.equals(body, clientePage));
    }

    @Test
    @DisplayName("Should be able to delete Cliente")
    public void should_be_able_to_delete_cliente(){

        Assertions.assertThatCode(() -> clienteController.deleteById(1)).doesNotThrowAnyException();

    }

    @Test
    @DisplayName("Should be able to create Cliente")
    public void should_be_able_to_create_cliente(){
        ClientePostRequest clientToBeSaved = CreateNewCliente.createValidClientPostRequest();

        String expectedBody = "Cliente already exists";
        Object body = clienteController.createNewCliente(clientToBeSaved).getBody();

        Assertions.assertThat(Objects.equals(body, expectedBody));
    }




}