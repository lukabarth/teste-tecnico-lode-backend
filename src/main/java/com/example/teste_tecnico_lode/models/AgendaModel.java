package com.example.teste_tecnico_lode.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "agenda")
public class AgendaModel implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long idAgenda;

    @Enumerated(EnumType.STRING)
    private DiaSemana diaSemana;

    private LocalTime horarioDisponivel;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "medico_id", referencedColumnName = "idMedico")
    private MedicoModel medico;

    @OneToOne(mappedBy = "agenda", cascade = CascadeType.ALL)
    @JsonManagedReference
    private ConsultaModel consulta;
}
