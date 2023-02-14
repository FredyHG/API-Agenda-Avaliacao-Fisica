package com.avaliacao.fisica.avp.services;

import com.avaliacao.fisica.avp.mapper.AvaliacaoMapper;
import com.avaliacao.fisica.avp.model.AvaliacaoModel;
import com.avaliacao.fisica.avp.model.ClienteModel;
import com.avaliacao.fisica.avp.repositories.AvaliacaoRepository;
import com.avaliacao.fisica.avp.requests.AvaliacaoPostRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {


    private final AvaliacaoRepository avaliacaoRepository;

    private final ClienteService clienteService;


    @Transactional
    public AvaliacaoModel saveNewAvaliacao(AvaliacaoPostRequest avaliacao){

        Optional<AvaliacaoModel> avaliacaoToBeSaved = Optional.of(AvaliacaoMapper.INSTANCE.toAvaliacao(avaliacao));

        Optional<ClienteModel> cliente = clienteService.findByCpf(avaliacao.getCpf());

        avaliacaoToBeSaved.get().setClientID(cliente.get().getId());

        return avaliacaoRepository.save(avaliacaoToBeSaved.get());
    }


}
