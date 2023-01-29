package com.avaliacao.fisica.avp.controllers;

import com.avaliacao.fisica.avp.model.ClienteModel;
import com.avaliacao.fisica.avp.requests.ClientePostRequest;
import com.avaliacao.fisica.avp.services.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Object> createNewCliente(@RequestBody @Valid ClientePostRequest cliente){

        Optional<ClienteModel> clienteSaved = clienteService.saveNewCliente(cliente);

        if(clienteSaved.isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cliente already exists");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSaved);

    }

    @GetMapping
    public ResponseEntity<Object> listAllClientes(Pageable pageable){
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.findAllClientPageable(pageable));
    }



}
