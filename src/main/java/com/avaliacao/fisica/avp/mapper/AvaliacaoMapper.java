package com.avaliacao.fisica.avp.mapper;

import com.avaliacao.fisica.avp.model.AvaliacaoModel;
import com.avaliacao.fisica.avp.model.ClienteModel;
import com.avaliacao.fisica.avp.requests.AvaliacaoPostRequest;
import com.avaliacao.fisica.avp.requests.ClientePostRequest;
import com.avaliacao.fisica.avp.requests.ClientePutRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class AvaliacaoMapper {

    public static final AvaliacaoMapper INSTANCE = Mappers.getMapper(AvaliacaoMapper.class);

    public abstract AvaliacaoModel toAvaliacao(AvaliacaoPostRequest avaliacao);



}
