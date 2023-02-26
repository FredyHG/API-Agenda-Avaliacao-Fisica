package com.avaliacao.fisica.avp.services;

import com.avaliacao.fisica.avp.model.AvaliacaoModel;
import com.avaliacao.fisica.avp.repositories.AvaliacaoRepository;
import com.avaliacao.fisica.avp.requests.AvaliacaoGetRequest;
import com.avaliacao.fisica.avp.requests.AvaliacaoPostRequest;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class AvaliacaoServiceTest {


    @InjectMocks
    private AvaliacaoService avaliacaoService;

    @Mock
    private AvaliacaoRepository avaliacaoRepositoryMock;

    @Mock
    private ClienteService clienteService;



    @BeforeEach
    void setUp(){

        PageImpl<AvaliacaoModel> avaliacaoPage = new PageImpl<>(List.of(AvaliacaoCreator.createValidAvaliacao()));

        BDDMockito.when(avaliacaoRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(avaliacaoPage);

        BDDMockito.when(avaliacaoRepositoryMock.save(ArgumentMatchers.any(AvaliacaoModel.class)))
                .thenReturn(AvaliacaoCreator.createValidAvaliacao());

        BDDMockito.when(avaliacaoRepositoryMock.findByClienteId(ArgumentMatchers.anyLong()))
                        .thenReturn(Optional.of(AvaliacaoCreator.createValidAvaliacao()));

        BDDMockito.when(clienteService.findByCpf(ArgumentMatchers.any()))
                .thenReturn(Optional.of(ClienteCreator.createValidClient()));

        BDDMockito.when(clienteService.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(ClienteCreator.createValidClient()));
    }

    @Test
    @DisplayName("Should be able to create Avaliacao")
    public void should_be_able_to_create_Avaliacao(){
        AvaliacaoPostRequest avaliacaoToBeSaved = AvaliacaoCreator.createValidAvaliacaoPostRequest();

        clienteService.saveNewCliente(ClienteCreator.createValidClientPostRequest());


        Optional<AvaliacaoModel> savedAvaliacao = avaliacaoService.saveNewAvaliacao(avaliacaoToBeSaved);


        Assertions.assertThat(savedAvaliacao.get().getClienteId()).isNotNull().isEqualTo(AvaliacaoCreator.createValidAvaliacao().getClienteId());
    }

    @Test
    @DisplayName("Should return a pageable list of 'Avaliacao'")
    public void should_return_a_pageable_list_of_Cliente(){
        String expectedAlergias = AvaliacaoCreator.createValidAvaliacao().getAlergias();

        Page<AvaliacaoGetRequest> avaliacaoPage = avaliacaoService.findAllAvaliacoesPageable(PageRequest.of(1,1));

        Assertions.assertThat(avaliacaoPage).isNotNull();

        Assertions.assertThat(avaliacaoPage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(avaliacaoPage.toList().get(0).getAlergias()).isEqualTo(expectedAlergias);
    }

    @Test
    @DisplayName("Should return a 'Avaliacao' by Cliente id")
    public void should_return_a_Avaliacao_by_Cliente_Id(){
        AvaliacaoModel expectedAvaliacao = AvaliacaoCreator.createValidAvaliacao();

        Optional<AvaliacaoModel> avaliacao = avaliacaoService.findByIdCliente(1L);

        Assertions.assertThat(avaliacao).isNotEmpty();

        Assertions.assertThat(avaliacao.get().getClienteId()).isEqualTo(expectedAvaliacao.getClienteId());
    }

    @Test
    @DisplayName("Should return a 'AvaliacaoDTO' by Cliente id")
    public void should_return_a_AvaliacaoDTO_by_Cliente_Id(){

        AvaliacaoGetRequest expectedAvaliacao = AvaliacaoCreator.createValidAvaliacaoGetRequest();

        Optional<AvaliacaoGetRequest> avaliacao = avaliacaoService.findByIdClienteDTO(1L);

        Assertions.assertThat(avaliacao).isNotEmpty();

        Assertions.assertThat(avaliacao.get().getLimitacoesFisicas()).isEqualTo(expectedAvaliacao.getLimitacoesFisicas());

    }






}