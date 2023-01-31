package com.avaliacao.fisica.avp.repositories;

import com.avaliacao.fisica.avp.model.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, Long> {

    @Query(value = "SELECT p FROM ClienteModel p WHERE p.nome = :nome AND p.sobrenome = :sobrenome")
    List<ClienteModel> findClientByNomeAndSobrenome(@Param("nome") String nome, @Param("sobrenome") String sobrenome);


}
