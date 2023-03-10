package com.avaliacao.fisica.avp.services;

import com.avaliacao.fisica.avp.mapper.AvaliacaoGetRequestMapper;
import com.avaliacao.fisica.avp.mapper.AvaliacaoMapper;
import com.avaliacao.fisica.avp.model.AvaliacaoModel;
import com.avaliacao.fisica.avp.model.ClienteModel;
import com.avaliacao.fisica.avp.repositories.AvaliacaoRepository;
import com.avaliacao.fisica.avp.requests.AvaliacaoGetRequest;
import com.avaliacao.fisica.avp.requests.AvaliacaoPostRequest;
import com.avaliacao.fisica.avp.requests.AvaliacaoPutRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;

    private final ClienteService clienteService;

    @Transactional
    public Optional<AvaliacaoModel> saveNewAvaliacao(AvaliacaoPostRequest avaliacao){

        Optional<AvaliacaoModel> avaliacaoToBeSaved = Optional.of(AvaliacaoMapper.INSTANCE.toAvaliacao(avaliacao));

        Optional<ClienteModel> cliente = clienteService.findByCpf(avaliacao.getClienteCpf());


        avaliacaoToBeSaved.get().setDataHora(avaliacao.getDataHora());

        cliente.ifPresent(clienteModel -> avaliacaoToBeSaved.get().setClienteId(clienteModel.getId()));

        AvaliacaoModel savedAvaliacao = avaliacaoRepository.save(avaliacaoToBeSaved.get());

        return Optional.of(savedAvaliacao);
    }


    public Optional<AvaliacaoModel> findByIdCliente(Long id) {
        return avaliacaoRepository.findByClienteId(id);
    }

    public Optional<AvaliacaoModel> findByIdClienteWithStatusTrue(Long id) {
        return avaliacaoRepository.findByIdIfStatusTrue(id);
    }



    public Optional<AvaliacaoGetRequest> findByIdClienteDTO(Long id) {


        Optional<AvaliacaoModel> avaliacaoExists  = avaliacaoRepository.findByClienteId(id);

        if(avaliacaoExists.isPresent()){
            AvaliacaoGetRequest avaliacao = AvaliacaoGetRequestMapper.INSTANCE.toAvaliacaoGetRequest(avaliacaoExists.get(), clienteService);
            return Optional.of(avaliacao);
        }

        return Optional.empty();
    }

    public Page<AvaliacaoGetRequest> findAllAvaliacoesPageable(Pageable pageable) {

        Page<AvaliacaoModel> pageableList = avaliacaoRepository.findAll(pageable);

        List<AvaliacaoGetRequest> avaliacaoGetRequest = AvaliacaoGetRequestMapper.INSTANCE.toAvaliacaoGetRequestList(pageableList.stream().toList(), clienteService);


        return new PageImpl<>(avaliacaoGetRequest);

    }

    public Optional<AvaliacaoGetRequest> replaceAvaliacao(AvaliacaoPutRequest avaliacao){

        Optional<AvaliacaoModel> avaliacaoToBeSaved = Optional.of(AvaliacaoMapper.INSTANCE.toAvaliacao(avaliacao));

        Optional<ClienteModel> cliente = clienteService.findByCpf(avaliacao.getClienteCpf());

        avaliacaoToBeSaved.get().setDataHora(avaliacao.getDataHora());

        cliente.ifPresent(clienteModel -> avaliacaoToBeSaved.get().setClienteId(clienteModel.getId()));

        AvaliacaoModel avaliacaoSaved = avaliacaoRepository.save(avaliacaoToBeSaved.get());

        AvaliacaoGetRequest avaliacaoGetRequest = AvaliacaoGetRequestMapper.INSTANCE.toAvaliacaoGetRequest(avaliacaoSaved, clienteService);

        return Optional.of(avaliacaoGetRequest);
    }



}
