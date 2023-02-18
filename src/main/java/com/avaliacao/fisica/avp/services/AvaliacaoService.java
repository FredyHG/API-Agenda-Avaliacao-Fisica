package com.avaliacao.fisica.avp.services;

import com.avaliacao.fisica.avp.mapper.AvaliacaoMapper;
import com.avaliacao.fisica.avp.model.AvaliacaoModel;
import com.avaliacao.fisica.avp.model.ClienteModel;
import com.avaliacao.fisica.avp.repositories.AvaliacaoRepository;
import com.avaliacao.fisica.avp.requests.AvaliacaoPostRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {


    private final AvaliacaoRepository avaliacaoRepository;

    private final ClienteService clienteService;


    @Transactional
    public Optional<AvaliacaoModel> saveNewAvaliacao(AvaliacaoPostRequest avaliacao){

        Optional<AvaliacaoModel> avaliacaoToBeSaved = Optional.of(AvaliacaoMapper.INSTANCE.toAvaliacao(avaliacao));

        Optional<ClienteModel> cliente = clienteService.findByCpf(avaliacao.getCpf());


        avaliacaoToBeSaved.get().setDataHora(Date.from(avaliacao.getDataHora().atZone(ZoneId.systemDefault()).toInstant()));

        avaliacaoToBeSaved.get().setClienteId(cliente.get().getId());

        AvaliacaoModel savedAvaliacao = avaliacaoRepository.save(avaliacaoToBeSaved.get());

        return Optional.of(savedAvaliacao);
    }


    public Optional<AvaliacaoModel> findByIdCliente(Long id) {
        return avaliacaoRepository.findByCpf(id);
    }
}
