package com.avaliacao.fisica.avp.mapper;

import com.avaliacao.fisica.avp.model.AvaliacaoModel;
import com.avaliacao.fisica.avp.model.ClienteModel;
import com.avaliacao.fisica.avp.requests.AvaliacaoGetRequest;
import com.avaliacao.fisica.avp.services.ClienteService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper(componentModel = "spring")
public interface AvaliacaoGetRequestMapper {


    AvaliacaoGetRequestMapper INSTANCE = Mappers.getMapper(AvaliacaoGetRequestMapper.class);


    @Mapping(source = "clienteId", target = "cliente")
    AvaliacaoGetRequest toAvaliacaoGetRequest(AvaliacaoModel avaliacaoModel, @Context ClienteService clienteService);

    default ClienteModel toClienteEntity(Long clienteId, @Context ClienteService clienteService) {
        return clienteService.findById(clienteId).get();
    }

    List<AvaliacaoGetRequest> toAvaliacaoGetRequestList(List<AvaliacaoModel> avaliacaoModelList, @Context ClienteService clienteService);
}
