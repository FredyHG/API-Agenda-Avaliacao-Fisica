package com.avaliacao.fisica.avp.services;

import com.avaliacao.fisica.avp.exception.BadRequestException;
import com.avaliacao.fisica.avp.mapper.ClienteMapper;
import com.avaliacao.fisica.avp.model.ClienteModel;
import com.avaliacao.fisica.avp.repositories.ClienteRepository;
import com.avaliacao.fisica.avp.requests.ClientePostRequest;
import com.avaliacao.fisica.avp.utils.TitleCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {


    private final ClienteRepository clienteRepository;


    @Transactional
    public Optional<ClienteModel> saveNewCliente(ClientePostRequest cliente){


        Optional<ClienteModel> clienteToBeSaved = Optional.of(ClienteMapper.INSTANCE.toCliente(cliente));

        clienteToBeSaved.get().setDataNascimento(Date.valueOf(cliente.getDataNascimento()));



        clienteToBeSaved.get().setNome(TitleCase.convertToTitleCaseIteratingChars(clienteToBeSaved.get().getNome()));
        clienteToBeSaved.get().setSobrenome(TitleCase.convertToTitleCaseIteratingChars(clienteToBeSaved.get().getSobrenome()));

        ClienteModel savedCliente = clienteRepository.save(clienteToBeSaved.get());

        return Optional.of(savedCliente);

    }

    public void deleteCliente(long id) {

        clienteRepository.delete(findByIdOrThrowBadRequestException(id));

    }

    public ClienteModel findByIdOrThrowBadRequestException(long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new BadRequestException("Cliente not Found"));
    }

    public Page<ClienteModel> findAllClientPageable(Pageable page){
        return clienteRepository.findAll(page);
    }

}
