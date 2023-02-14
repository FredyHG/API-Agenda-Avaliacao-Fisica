package com.avaliacao.fisica.avp.repositories;

import com.avaliacao.fisica.avp.model.AvaliacaoModel;
import com.avaliacao.fisica.avp.model.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliacaoRepository extends JpaRepository<AvaliacaoModel, Long> {
}
