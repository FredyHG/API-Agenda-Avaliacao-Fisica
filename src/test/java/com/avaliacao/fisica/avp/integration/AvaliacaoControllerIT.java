package com.avaliacao.fisica.avp.integration;

import com.avaliacao.fisica.avp.model.AvaliacaoModel;
import com.avaliacao.fisica.avp.repositories.AvaliacaoRepository;
import com.avaliacao.fisica.avp.repositories.ClienteRepository;
import com.avaliacao.fisica.avp.requests.AvaliacaoGetRequest;
import com.avaliacao.fisica.avp.requests.AvaliacaoPostRequest;
import com.avaliacao.fisica.avp.utils.AvaliacaoCreator;
import com.avaliacao.fisica.avp.utils.ClienteCreator;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AvaliacaoControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    @DisplayName("Should return a pageable list of 'Avaliacao'")
    public void should_return_a_pageable_list_of_Cliente(){

        clienteRepository.save(ClienteCreator.createValidClient());

        AvaliacaoModel savedAvaliacao = avaliacaoRepository.save(AvaliacaoCreator.createValidAvaliacao());

        long expectedId = savedAvaliacao.getClienteId();

        PageableResponse<AvaliacaoGetRequest> avaliacaoGetPage = testRestTemplate.exchange("/api/avaliacao/list", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<AvaliacaoGetRequest>>() {
                }).getBody();

        Assertions.assertThat(avaliacaoGetPage).isNotNull();

        Assertions.assertThat(avaliacaoGetPage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(avaliacaoGetPage.toList().get(0).getCliente().getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("save returns Cliente when successful")
    void save_ReturnsCliente_WhenSuccessful(){

        clienteRepository.save(ClienteCreator.createValidClient());

        AvaliacaoPostRequest avaliacaoPostRequest = AvaliacaoCreator.createValidAvaliacaoPostRequest();
        String expectedBody = "Avaliacao created successfully";

        ResponseEntity<String> avaliacao = testRestTemplate.postForEntity("/api/avaliacao/new", avaliacaoPostRequest, String.class);

        Assertions.assertThat(avaliacao).isNotNull();
        Assertions.assertThat(avaliacao.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(avaliacao.getBody()).isNotNull();
        Assertions.assertThat(avaliacao.getBody()).isEqualTo(expectedBody);

    }

    @Test
    @DisplayName("Return a 'AvaliacaoGetDto' when successful")
    void return_A_AvaliacaoGetDto_When_Successful(){

        String url = "http://localhost:" + this.port;

        clienteRepository.save(ClienteCreator.createValidClient());
        avaliacaoRepository.save(AvaliacaoCreator.createValidAvaliacao());

        AvaliacaoGetRequest expectedBody = AvaliacaoCreator.createValidAvaliacaoGetRequest();



        URI uri = UriComponentsBuilder.fromHttpUrl(url).path("/api/avaliacao/find")
                .queryParam("cpf", "123.123.123-12").build().toUri();

        AvaliacaoGetRequest avaliacao = testRestTemplate.exchange(uri, HttpMethod.GET, null,
                new ParameterizedTypeReference<AvaliacaoGetRequest>() {
                }).getBody();


        Assertions.assertThat(avaliacao).isNotNull();
        Assertions.assertThat(avaliacao.getAlergias()).isEqualTo(expectedBody.getAlergias());
    }


}
