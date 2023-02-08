package com.avaliacao.fisica.avp.mapper;

import com.avaliacao.fisica.avp.model.ClienteModel;
import com.avaliacao.fisica.avp.requests.ClientePostRequest;
import com.avaliacao.fisica.avp.requests.ClientePutRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class ClienteMapper {

    public static final ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    public abstract ClienteModel toCliente(ClientePostRequest cliente);

    public abstract ClienteModel toCliente(ClientePutRequest cliente);

}
