package com.avaliacao.fisica.avp.mapper;

import com.avaliacao.fisica.avp.model.AvaliacaoModel;
import com.avaliacao.fisica.avp.requests.AvaliacaoPostRequest;
import com.avaliacao.fisica.avp.requests.AvaliacaoPutRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class AvaliacaoMapper {

    public static final AvaliacaoMapper INSTANCE = Mappers.getMapper(AvaliacaoMapper.class);

    public abstract AvaliacaoModel toAvaliacao(AvaliacaoPostRequest avaliacao);

    public abstract AvaliacaoModel toAvaliacao(AvaliacaoPutRequest avaliacao);




}
