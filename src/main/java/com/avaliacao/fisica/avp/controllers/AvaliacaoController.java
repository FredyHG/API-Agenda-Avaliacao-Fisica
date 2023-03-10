package com.avaliacao.fisica.avp.controllers;

import com.avaliacao.fisica.avp.model.AvaliacaoModel;
import com.avaliacao.fisica.avp.model.ClienteModel;
import com.avaliacao.fisica.avp.requests.AvaliacaoGetRequest;
import com.avaliacao.fisica.avp.requests.AvaliacaoPostRequest;
import com.avaliacao.fisica.avp.requests.AvaliacaoPutRequest;
import com.avaliacao.fisica.avp.services.AvaliacaoService;
import com.avaliacao.fisica.avp.services.ClienteService;
import com.avaliacao.fisica.avp.utils.RegexChecker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/avaliacao")
@RequiredArgsConstructor
public class AvaliacaoController {

    private final ClienteService clienteService;

    private final AvaliacaoService avaliacaoService;

    @PostMapping("/new")
    public ResponseEntity<Object> createNewAvaliacao(@RequestBody @Valid AvaliacaoPostRequest avaliacao) {

        if (avaliacao.getDataHora().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid date, check the parameters and try again");
        }

        if(!RegexChecker.isValidCPF(avaliacao.getClienteCpf())){
            return cpfIsInvalid();
        }

        Optional<ClienteModel> cliente = clienteService.findByCpf(avaliacao.getClienteCpf());

        if(cliente.isEmpty()){
            return clienteNotExists();
        }

        Optional<AvaliacaoModel> avaliacaoExists = avaliacaoService.findByIdCliente(cliente.get().getId());

        if(avaliacaoExists.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente is already registered in a pending avaliação!");
        }

        avaliacaoService.saveNewAvaliacao(avaliacao);

        return ResponseEntity.status(HttpStatus.CREATED).body("Avaliacao created successfully");
    }

    @GetMapping("/list")
    public ResponseEntity<Page<AvaliacaoGetRequest>> listAllAvaliacoes(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(avaliacaoService.findAllAvaliacoesPageable(pageable));
    }

    @GetMapping("/find")
    public ResponseEntity<Object> findByCpf(@RequestParam(value = "cpf") String cpf){

        if(!RegexChecker.isValidCPF(cpf)){
            return cpfIsInvalid();
        }

        Optional<ClienteModel> cliente = clienteService.findByCpf(cpf);

        if(cliente.isEmpty()){
            return clienteNotExists();
        }

        Optional<AvaliacaoGetRequest> avaliacaoExists = avaliacaoService.findByIdClienteDTO(cliente.get().getId());

        if(avaliacaoExists.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Avaliacao not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(avaliacaoExists.get());
    }

    @PutMapping("/edit")
    public ResponseEntity<Object> editAvaliacao(@RequestBody @Valid AvaliacaoPutRequest avaliacao){

        if(RegexChecker.isValidCPF(avaliacao.getClienteCpf())){
            return cpfIsInvalid();
        }

        Optional<ClienteModel> cliente = clienteService.findByCpf(avaliacao.getClienteCpf());

        if(cliente.isEmpty()){
            return clienteNotExists();
        }

        Optional<AvaliacaoModel> avaliacaoExists = avaliacaoService.findByIdClienteWithStatusTrue(cliente.get().getId());

        if(avaliacaoExists.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Pending Avaliacao not found");
        }

        if(avaliacaoExists.get().getDataHora().isBefore(avaliacao.getDataHora())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid date");
        }

        if(!Objects.equals(avaliacao.getClienteCpf(), cliente.get().getCpf())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You cannot change the client of the Avaliacao");
        }

        avaliacaoService.replaceAvaliacao(avaliacao);

        return ResponseEntity.status(HttpStatus.OK).body("Avaliacao updated successfully");
    }


    private static ResponseEntity<Object> cpfIsInvalid(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF is not valid");
    }

    private static ResponseEntity<Object> clienteNotExists(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente not exists!");
    }
}
