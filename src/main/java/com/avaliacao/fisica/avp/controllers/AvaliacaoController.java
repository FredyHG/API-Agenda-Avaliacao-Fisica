package com.avaliacao.fisica.avp.controllers;

import com.avaliacao.fisica.avp.model.AvaliacaoModel;
import com.avaliacao.fisica.avp.model.ClienteModel;
import com.avaliacao.fisica.avp.requests.AvaliacaoPostRequest;
import com.avaliacao.fisica.avp.services.AvaliacaoService;
import com.avaliacao.fisica.avp.services.ClienteService;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/avaliacao")
@RequiredArgsConstructor
public class AvaliacaoController {

    private final ClienteService clienteService;

    private final AvaliacaoService avaliacaoService;



    @PostMapping
    public ResponseEntity<Object> createNewAvaliacao(AvaliacaoPostRequest avaliacao) {
        Optional<ClienteModel> cliente = clienteService.findByCpf(avaliacao.getCpf());

        if(cliente.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente not exists!");
        }

        Optional<AvaliacaoModel> avaliacaoExists = avaliacaoService.findByIdCliente(cliente.get().getId());

        if(avaliacaoExists.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente is already registered in a pending avaliação!");
        }


        Optional<AvaliacaoModel> savedAvaliacao = avaliacaoService.saveNewAvaliacao(avaliacao);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedAvaliacao);
    }
}
