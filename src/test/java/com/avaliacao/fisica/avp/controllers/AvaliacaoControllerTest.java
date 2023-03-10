package com.avaliacao.fisica.avp.controllers;

import com.avaliacao.fisica.avp.model.ClienteModel;
import com.avaliacao.fisica.avp.requests.AvaliacaoGetRequest;
import com.avaliacao.fisica.avp.requests.AvaliacaoPostRequest;
import com.avaliacao.fisica.avp.services.AvaliacaoService;
import com.avaliacao.fisica.avp.services.ClienteService;
import com.avaliacao.fisica.avp.utils.AvaliacaoCreator;
import com.avaliacao.fisica.avp.utils.ClienteCreator;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;


@ExtendWith(SpringExtension.class)
class AvaliacaoControllerTest {

    @InjectMocks
    private AvaliacaoController avaliacaoController;

    @Mock
    private AvaliacaoService avaliacaoServiceMock;

    @Mock
    private ClienteService clienteServiceMock;

    @BeforeEach
    void setUp(){
        PageImpl<AvaliacaoGetRequest> avaliacaoPage = new PageImpl<>(List.of(AvaliacaoCreator.createValidAvaliacaoGetRequest()));

        BDDMockito.when(avaliacaoServiceMock.findAllAvaliacoesPageable(ArgumentMatchers.any()))
                .thenReturn(avaliacaoPage);

        BDDMockito.when(avaliacaoServiceMock.findByIdClienteDTO(ArgumentMatchers.anyLong()))
                        .thenReturn(Optional.of(AvaliacaoCreator.createValidAvaliacaoGetRequest()));

        BDDMockito.when(clienteServiceMock.findByCpf(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(ClienteCreator.createValidClient()));
    }

    @Test
    @DisplayName("Should return a pageable list of 'Avaliacao'")
    void should_return_a_pageable_list_of_Cliente(){
        ClienteModel expectedCliente = ClienteCreator.createValidClient();

        Page<AvaliacaoGetRequest> avaliacaoPage = avaliacaoController.listAllAvaliacoes(null).getBody();

        Assertions.assertThat(avaliacaoPage).isNotNull();

        Assertions.assertThat(avaliacaoPage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(avaliacaoPage.toList().get(0).getCliente()).isEqualTo(expectedCliente);
    }

    @Test
    @DisplayName("Should be able to create Cliente")
    void should_be_able_to_create_cliente(){
        AvaliacaoPostRequest avaliacaoToBeSaved = AvaliacaoCreator.createValidAvaliacaoPostRequest();

        String expectedString = "Avaliacao created successfully";
        String expectedBody  = avaliacaoController.createNewAvaliacao(avaliacaoToBeSaved).getBody().toString();

        Assertions.assertThat(expectedBody).isEqualTo(expectedString);
    }

    @Test
    @DisplayName("Should return a 'AvaliacaoDTO' by Cliente id")
    void should_return_a_AvaliacaoDTO_by_Cliente_Id(){

        ClienteModel cliente = ClienteCreator.createValidClient();
        cliente.setId(1L);

        cliente.setCpf("123");

        BDDMockito.when(clienteServiceMock.findByCpf(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(cliente));


        ResponseEntity<Object> bodyAvaliacao = avaliacaoController.findByCpf("123.123.123-12");

        Assertions.assertThat(bodyAvaliacao.getStatusCode()).isEqualTo(HttpStatus.OK);

    }
}