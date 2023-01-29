package com.avaliacao.fisica.avp.services;

import com.avaliacao.fisica.avp.exception.BadRequestException;
import com.avaliacao.fisica.avp.model.ClienteModel;
import com.avaliacao.fisica.avp.repositories.ClienteRepository;
import com.avaliacao.fisica.avp.requests.ClientePostRequest;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepositoryMock;



    @BeforeEach
    void setUp(){
        PageImpl<ClienteModel> clientePage = new PageImpl<>(List.of(CreateNewCliente.createValidClient()));

        BDDMockito.when(clienteRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(clientePage);

        BDDMockito.when(clienteRepositoryMock.save(ArgumentMatchers.any(ClienteModel.class)))
                .thenReturn(CreateNewCliente.createValidClient());

        BDDMockito.doNothing().when(clienteRepositoryMock).delete(ArgumentMatchers.any(ClienteModel.class));

        BDDMockito.when(clienteRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(CreateNewCliente.createValidClient()));
    }




    @Test
    @DisplayName("Should be able to create Cliente")
    public void should_be_able_to_create_cliente(){
        ClientePostRequest clientToBeSaved = CreateNewCliente.createValidClientPostRequest();

        Optional<ClienteModel> savedCliente = clienteService.saveNewCliente(clientToBeSaved);

        Assertions.assertThat(savedCliente.get()).isNotNull().isEqualTo(CreateNewCliente.createValidClient());
    }

    @Test
    public void findById_ThrowsBadRequestException_WhenClienteIsNotFound(){
        BDDMockito.when(clienteRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> clienteService.findByIdOrThrowBadRequestException(1));
    }

    @Test
    @DisplayName("Should be able to delete Cliente")
    public void should_be_able_to_delete_cliente(){

        Assertions.assertThatCode(() -> clienteService.deleteCliente(1)).doesNotThrowAnyException();

    }

    @Test
    @DisplayName("Should return a pageable list of 'Clientes'")
    public void should_return_a_pageable_list_of_Clientes(){
        String expectedName = CreateNewCliente.createValidClient().getNome();

        Page<ClienteModel> clientePage = clienteService.findAllClientPageable(PageRequest.of(1,1));

        Assertions.assertThat(clientePage).isNotNull();

        Assertions.assertThat(clientePage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(clientePage.toList().get(0).getNome()).isEqualTo(expectedName);
    }


}