package com.avaliacao.fisica.avp.services;

import com.avaliacao.fisica.avp.model.AvaliacaoModel;
import com.avaliacao.fisica.avp.repositories.AvaliacaoRepository;
import com.avaliacao.fisica.avp.repositories.ClienteRepository;
import com.avaliacao.fisica.avp.requests.AvaliacaoPostRequest;
import com.avaliacao.fisica.avp.requests.ClientePostRequest;
import com.avaliacao.fisica.avp.utils.AvaliacaoCreator;
import com.avaliacao.fisica.avp.utils.ClienteCreator;
import org.assertj.core.api.Assertions;
import org.checkerframework.checker.nullness.Opt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
        BDDMockito.when(avaliacaoRepositoryMock.save(ArgumentMatchers.any(AvaliacaoModel.class)))
                .thenReturn(AvaliacaoCreator.createValidAvaliacao());

        BDDMockito.when(clienteService.findByCpf(ArgumentMatchers.any()))
                .thenReturn(Optional.of(ClienteCreator.createValidClient()));
    }

    @Test
    @DisplayName("Should be able to create Avaliacao")
    public void should_be_able_to_create_Avaliacao(){
        AvaliacaoPostRequest avaliacaoToBeSaved = AvaliacaoCreator.createValidAvaliacaoPostRequest();

        clienteService.saveNewCliente(ClienteCreator.createValidClientPostRequest());

        Optional<AvaliacaoModel> savedAvaliacao = avaliacaoService.saveNewAvaliacao(avaliacaoToBeSaved);

        System.out.println(savedAvaliacao);

        Assertions.assertThat(savedAvaliacao.get()).isNotNull().isEqualTo(AvaliacaoCreator.createValidAvaliacao());
    }


}