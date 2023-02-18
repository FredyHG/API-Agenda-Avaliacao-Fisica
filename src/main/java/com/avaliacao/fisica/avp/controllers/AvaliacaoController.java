package com.avaliacao.fisica.avp.controllers;

import com.avaliacao.fisica.avp.model.AvaliacaoModel;
import com.avaliacao.fisica.avp.model.ClienteModel;
import com.avaliacao.fisica.avp.requests.AvaliacaoPostRequest;
import com.avaliacao.fisica.avp.services.AvaliacaoService;
import com.avaliacao.fisica.avp.services.ClienteService;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/avaliacao")
@RequiredArgsConstructor
public class AvaliacaoController {

    private final ClienteService clienteService;

    private final AvaliacaoService avaliacaoService;



    @PostMapping
    public ResponseEntity<Object> createNewAvaliacao(@RequestBody @Valid AvaliacaoPostRequest avaliacao) {


        if (avaliacao.getDataHora().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid date, check the parameters and try again");
        }

        Optional<ClienteModel> cliente = clienteService.findByCpf(avaliacao.getCpf());

        if(cliente.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente not exists!");
        }

        Pattern pattern = Pattern.compile("^[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}\\-[0-9]{2}$");
        Matcher matcher = pattern.matcher(cliente.get().getCpf());

        if(!matcher.find()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF is not valid");
        }

        Optional<AvaliacaoModel> avaliacaoExists = avaliacaoService.findByIdCliente(cliente.get().getId());

        if(avaliacaoExists.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente is already registered in a pending avaliação!");
        }

        Optional<AvaliacaoModel> savedAvaliacao = avaliacaoService.saveNewAvaliacao(avaliacao);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedAvaliacao);
    }
}
