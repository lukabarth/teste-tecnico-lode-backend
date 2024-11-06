package com.example.teste_tecnico_lode.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "laboratorio")
public class MedicoModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String conselho;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private String cpf;

    @JsonBackReference
    @OneToOne(mappedBy = "medico")
    private AgendaModel agenda;
}