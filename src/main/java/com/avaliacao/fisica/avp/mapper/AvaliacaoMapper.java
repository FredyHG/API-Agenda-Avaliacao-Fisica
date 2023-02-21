package com.avaliacao.fisica.avp.mapper;

import com.avaliacao.fisica.avp.model.AvaliacaoModel;
import com.avaliacao.fisica.avp.requests.AvaliacaoGetRequest;
import com.avaliacao.fisica.avp.requests.AvaliacaoPostRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class AvaliacaoMapper {

    public static final AvaliacaoMapper INSTANCE = Mappers.getMapper(AvaliacaoMapper.class);

    public abstract AvaliacaoModel toAvaliacao(AvaliacaoPostRequest avaliacao);




}
