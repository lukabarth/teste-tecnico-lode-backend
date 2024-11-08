package com.example.teste_tecnico_lode.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "pacientes")
public class PacienteModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long idPaciente;

    private String nome;

    private int idade;

    private String estado;

    private String cidade;

    @Column(unique = true)
    private String cpf;

    @ManyToMany(mappedBy = "pacientes", fetch = FetchType.LAZY)
    private List<ConsultaModel> consultas;
}
;