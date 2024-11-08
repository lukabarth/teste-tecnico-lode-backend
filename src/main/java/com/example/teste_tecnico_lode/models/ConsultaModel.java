package com.example.teste_tecnico_lode.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "consultas")
public class ConsultaModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long idConsulta;

    private LocalDate dataConsulta;

    @JoinTable(
            name = "consulta_paciente",
            joinColumns = @JoinColumn(name = "consulta_id"),
            inverseJoinColumns = @JoinColumn(name = "paciente_id")
    )
    private List<PacienteModel> pacientes;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "id_agenda")
    private AgendaModel agenda;
}
