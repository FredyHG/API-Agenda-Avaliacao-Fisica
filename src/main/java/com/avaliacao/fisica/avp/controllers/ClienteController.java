package com.avaliacao.fisica.avp.controllers;

import com.avaliacao.fisica.avp.model.ClienteModel;
import com.avaliacao.fisica.avp.requests.ClientePostRequest;
import com.avaliacao.fisica.avp.requests.ClientePutRequest;
import com.avaliacao.fisica.avp.services.ClienteService;
import com.avaliacao.fisica.avp.utils.RegexChecker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

        return clienteNotExists();
    }

    @GetMapping("/find")
    public ResponseEntity<Object> findByName(@RequestParam(value = "name") String nome, @RequestParam(value = "sobrenome") String sobrenome){
       List<ClienteModel> cliente = clienteService.findByNomeAndSobrenome(nome, sobrenome);

        if(cliente.isEmpty()){
            return clienteNotExists();
        }

        return ResponseEntity.status(HttpStatus.OK).body(cliente);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable long id){
        Optional<ClienteModel> clienteExist = clienteService.findById(id);

        if(clienteExist.isEmpty()){
            return clienteNotExists();
        }

        clienteService.deleteCliente(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cliente deleted successfully");

    }

    @PutMapping("/edit")
    public ResponseEntity<Object> editCliente(@RequestBody ClientePutRequest cliente){

        Optional<ClienteModel> clienteExists = clienteService.findByCpf(cliente.getCpf());

        if (clienteExists.isEmpty()){
            return clienteNotExists();
        }

        clienteService.replace(cliente);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cliente edited successfully");

    }

    @PostMapping("/new")
    public ResponseEntity<String> createNewCliente(@RequestBody @Valid ClientePostRequest cliente){

        if(!RegexChecker.isValidCPF(cliente.getCpf())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF is not valid");
        }

        if (cliente.getDataNascimento().isAfter(LocalDate.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid birthdate, check the parameters and try again");
        }

        Optional<ClienteModel> clienteExists = clienteService.findByCpf(cliente.getCpf());

        if(clienteExists.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cliente already exists");
        }

        clienteService.saveNewCliente(cliente);

        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente created successfully");

    }

    private static ResponseEntity<Object> clienteNotExists(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente not exists!");
    }

}
