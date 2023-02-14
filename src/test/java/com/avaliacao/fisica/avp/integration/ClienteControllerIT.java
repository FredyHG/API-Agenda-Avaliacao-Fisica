package com.avaliacao.fisica.avp.integration;

import com.avaliacao.fisica.avp.model.ClienteModel;
import com.avaliacao.fisica.avp.repositories.ClienteRepository;
import com.avaliacao.fisica.avp.requests.ClientePostRequest;
import com.avaliacao.fisica.avp.requests.ClientePutRequest;
import com.avaliacao.fisica.avp.utils.CreateNewCliente;
import com.avaliacao.fisica.avp.wrapper.PageableResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;


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


    @Test
    @DisplayName("Should return a List of 'Cliente' by NomeAndSobrenome")
    public void should_return_a_List_of_Cliente_by_NomeAndSobrenome() {

        String url = "http://localhost:" + this.port;

        URI uri = UriComponentsBuilder.fromHttpUrl(url).path("/api/cliente/find")
                .queryParam("name", "Cliente_nome").queryParam("sobrenome", "Cliente_sobrenome").build().toUri();


        ClienteModel clienteExpected = clienteRepository.save(CreateNewCliente.createValidClient());


        List<ClienteModel> clienteList = testRestTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<ClienteModel>>() {}).getBody();


        Assertions.assertThat(clienteList.get(0).getNome()).isEqualTo(clienteExpected.getNome());

        Assertions.assertThat(clienteList.get(0).getSobrenome()).isEqualTo(clienteExpected.getSobrenome());

        Assertions.assertThat(clienteList.get(0).getId()).isEqualTo(clienteExpected.getId());


    }

    @Test
    @DisplayName("Should return a ResponseEntity with the message 'Cliente not found'")
    public void Should_return_a_ReponseEntity_with_the_message_Cliente_not_found() {

        String url = "http://localhost:" + this.port;

        URI uri = UriComponentsBuilder.fromHttpUrl(url).path("/api/cliente/find")
                .queryParam("name", "Cliente_nome").queryParam("sobrenome", "Cliente_sobrenome").build().toUri();

        ResponseEntity<String> clienteList = testRestTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
        });

        System.out.println(clienteList);

        Assertions.assertThat(clienteList).isNotNull();

        Assertions.assertThat(clienteList.getStatusCode().is4xxClientError());

        Assertions.assertThat(clienteList.getBody()).isEqualTo("Cliente not found");


    }

    @Test
    @DisplayName("save returns Cliente when successful")
    void save_ReturnsCliente_WhenSuccessful(){
        ClientePostRequest clientePostRequestBody = CreateNewCliente.createValidClientPostRequest();

        ResponseEntity<ClienteModel> cliente = testRestTemplate.postForEntity("/api/cliente/new", clientePostRequestBody, ClienteModel.class);

        Assertions.assertThat(cliente).isNotNull();
        Assertions.assertThat(cliente.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(cliente.getBody()).isNotNull();
        Assertions.assertThat(cliente.getBody().getId()).isNotNull();

    }

    @Test
    @DisplayName("Should_return_a_HttpStatusCode_204_NO_CONTENT_and_update_Cliente")
    public void should_return_a_HttpStatusCode_204_NO_CONTENT_and_update_cliente() {

        ClienteModel cliente = clienteRepository.save(CreateNewCliente.createValidClient());

        ClientePutRequest clienteToBeEdit = CreateNewCliente.createValidPutRequest();

        ResponseEntity<String> response = testRestTemplate.exchange("/api/cliente/edit", HttpMethod.PUT, new HttpEntity<>(clienteToBeEdit), String.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Assertions.assertThat(response.getBody()).isNull();

        Optional<ClienteModel> clienteEditado = clienteRepository.findById(1L);
        Assertions.assertThat(clienteEditado).isPresent();
        Assertions.assertThat(clienteEditado.get().getNome()).isEqualTo("Cliente_nome_mod");
        Assertions.assertThat(clienteEditado.get().getTelefone()).isEqualTo("(37)020202020");
    }

    @Test
    @DisplayName("Should_return_a_HttpStatusCode_204_NO_CONTENT_and_delete_Cliente")
    public void should_return_a_HttpStatusCode_204_NO_CONTENT_and_delete_cliente() {

        String url = "http://localhost:" + this.port + "/api/cliente/delete/1";

        clienteRepository.save(CreateNewCliente.createValidClient());

        ResponseEntity<String> response = testRestTemplate.exchange(url, HttpMethod.DELETE, null, String.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Assertions.assertThat(response.getBody()).isNull();

        Optional<ClienteModel> clienteDeletado = clienteRepository.findById(1L);
        Assertions.assertThat(clienteDeletado).isEmpty();
    }

}
