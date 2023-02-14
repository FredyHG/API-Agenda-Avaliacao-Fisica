package com.avaliacao.fisica.avp.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;


@Entity(name = "avaliacao")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AvaliacaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "datahora222")
    private Date dataHora;

    @Column(name = "status")
    private boolean status;

    @Column(name = "alergias")
    private String alergias;

    @Column(name = "limitacoesfisica")
    private String limitacoesFisicas;

    @Column(name = "clientId")
    private Long clientID;

}
