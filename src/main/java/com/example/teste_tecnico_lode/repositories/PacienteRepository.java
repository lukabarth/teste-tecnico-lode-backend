package com.example.teste_tecnico_lode.repositories;

import com.example.teste_tecnico_lode.models.PacienteModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<PacienteModel, Long> {
}
