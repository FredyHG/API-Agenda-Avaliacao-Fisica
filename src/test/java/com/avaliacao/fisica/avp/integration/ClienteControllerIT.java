package com.avaliacao.fisica.avp.integration;

import com.avaliacao.fisica.avp.model.ClienteModel;
import com.avaliacao.fisica.avp.repositories.ClienteRepository;
import com.avaliacao.fisica.avp.utils.CreateNewCliente;
import com.avaliacao.fisica.avp.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ClienteControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    @DisplayName("Should return a pageable list of 'Clientes'")
    public void should_return_a_pageable_list_of_Cliente(){

        ClienteModel savedCliente = clienteRepository.save(CreateNewCliente.createValidClient());

        String expectedName = savedCliente.getNome();

        PageableResponse<ClienteModel> clientePage = testRestTemplate.exchange("/api/cliente/list", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<ClienteModel>>() {
                }).getBody();

        Assertions.assertThat(clientePage).isNotNull();

        Assertions.assertThat(clientePage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(clientePage.toList().get(0).getNome()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Should return a 'Cliente' by ID")
    public void should_Return_a_Cliente_by_id(){
        ClienteModel savedCliente = clienteRepository.save(CreateNewCliente.createValidClient());

        long expectedId = savedCliente.getId();

        ClienteModel cliente = testRestTemplate.getForObject("/api/cliente/{id}", ClienteModel.class, expectedId);

        Assertions.assertThat(cliente).isNotNull();

        Assertions.assertThat(cliente.getId()).isNotNull().isEqualTo(expectedId);
    }




}
