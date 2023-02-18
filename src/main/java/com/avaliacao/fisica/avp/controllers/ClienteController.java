package com.avaliacao.fisica.avp.controllers;

import com.avaliacao.fisica.avp.model.ClienteModel;
import com.avaliacao.fisica.avp.requests.ClientePostRequest;
import com.avaliacao.fisica.avp.requests.ClientePutRequest;
import com.avaliacao.fisica.avp.services.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping("/list")
    public ResponseEntity<Page<ClienteModel>> listAllClientes(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.findAllClientPageable(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable long id){
        Optional<ClienteModel> byId = clienteService.findById(id);
        if(byId.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(byId.get());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente not found");
    }

    @GetMapping("/find")
    public ResponseEntity<Object> findByName(@RequestParam(value = "name") String nome, @RequestParam(value = "sobrenome") String sobrenome){
       List<ClienteModel> cliente = clienteService.findByNomeAndSobrenome(nome, sobrenome);

        if(cliente.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(cliente);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable long id){
        Optional<ClienteModel> clienteExist = clienteService.findById(id);

        if(clienteExist.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente not found");
        }

        clienteService.deleteCliente(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cliente deleted successfully");

    }

    @PutMapping("/edit")
    public ResponseEntity<Object> editCliente(@RequestBody ClientePutRequest cliente){

        Optional<ClienteModel> clienteExists = clienteService.findByCpf(cliente.getCpf());

        if (clienteExists.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente not found");
        }

        clienteService.replace(cliente);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cliente edited successfully");

    }

    @PostMapping("/new")
    public ResponseEntity<Object> createNewCliente(@RequestBody @Valid ClientePostRequest cliente){

        Pattern pattern = Pattern.compile("^[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}\\-[0-9]{2}$");
        Matcher matcher = pattern.matcher(cliente.getCpf());

        if(!matcher.find()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF is not valid");
        }

        if (cliente.getDataNascimento().isAfter(LocalDate.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid birthdate, check the parameters and try again");
        }

        Optional<ClienteModel> clienteExists = clienteService.findByCpf(cliente.getCpf());

        if(clienteExists.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cliente already exists");
        }

        Optional<ClienteModel> clienteSaved = clienteService.saveNewCliente(cliente);

        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSaved);

    }



}
