package com.avaliacao.fisica.avp.repositories;

import com.avaliacao.fisica.avp.model.AvaliacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AvaliacaoRepository extends JpaRepository<AvaliacaoModel, Long> {

    @Query(value = "SELECT p FROM avaliacao p WHERE p.clienteId = :id AND p.status = false")
    Optional<AvaliacaoModel> findByCpf(Long id);

    @Query(value = "SELECT p FROM avaliacao p WHERE p.clienteId = :id AND p.status = true")
    Optional<AvaliacaoModel> findByCpfIfStatusTrue(Long id);

}