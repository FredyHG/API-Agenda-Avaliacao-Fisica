package com.avaliacao.fisica.avp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity(name = "cliente")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteModel {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     @Column(name = "nome")
     private String nome;

     @Column(name = "sobrenome")
     private String sobrenome;

     @Column(name = "nascimentodate")
     private Date dataNascimento;

     @Column(name = "idade")
     private Long idade;

     @Column(name = "telefone")
     private String telefone;

     @Column(name = "cpf")
     private String cpf;

}
