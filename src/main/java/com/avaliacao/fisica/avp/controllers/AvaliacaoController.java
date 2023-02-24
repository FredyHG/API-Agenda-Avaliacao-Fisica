package com.avaliacao.fisica.avp.controllers;

import com.avaliacao.fisica.avp.model.AvaliacaoModel;
import com.avaliacao.fisica.avp.model.ClienteModel;
import com.avaliacao.fisica.avp.requests.AvaliacaoGetRequest;
import com.avaliacao.fisica.avp.requests.AvaliacaoPostRequest;
import com.avaliacao.fisica.avp.services.AvaliacaoService;
import com.avaliacao.fisica.avp.services.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/avaliacao")
@RequiredArgsConstructor
public class AvaliacaoController {

    private final ClienteService clienteService;

    private final AvaliacaoService avaliacaoService;



    @PostMapping("new")
    public ResponseEntity<String> createNewAvaliacao(@RequestBody @Valid AvaliacaoPostRequest avaliacao) {


        if (avaliacao.getDataHora().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid date, check the parameters and try again");
        }

        Pattern pattern = Pattern.compile("^[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}\\-[0-9]{2}$");
        Matcher matcher = pattern.matcher(avaliacao.getCpf());

        if(!matcher.find()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF is not valid");
        }

        Optional<ClienteModel> cliente = clienteService.findByCpf(avaliacao.getCpf());

        if(cliente.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente not exists!");
        }

        Optional<AvaliacaoModel> avaliacaoExists = avaliacaoService.findByIdCliente(cliente.get().getId());

        if(avaliacaoExists.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente is already registered in a pending avaliação!");
        }

        avaliacaoService.saveNewAvaliacao(avaliacao);

        return ResponseEntity.status(HttpStatus.CREATED).body("Avaliacao created successfully");
    }

    @GetMapping("list")
    public ResponseEntity<Page<AvaliacaoGetRequest>> listAllAvaliacoes(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(avaliacaoService.findAllAvaliacoesPageable(pageable));
    }

    @GetMapping("find")
    public ResponseEntity<Object> findByCpf(@RequestParam(value = "cpf") String cpf){

        Pattern pattern = Pattern.compile("^[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}\\-[0-9]{2}$");
        Matcher matcher = pattern.matcher(cpf);

        if(!matcher.find()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF is not valid");
        }

        Optional<ClienteModel> cliente = clienteService.findByCpf(cpf);

        if(cliente.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente not exists!");
        }

        Optional<AvaliacaoGetRequest> avaliacaoExists = avaliacaoService.findByIdClienteDTO(cliente.get().getId());

        if(avaliacaoExists.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Avaliacao not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(avaliacaoExists.get());
    }


}
