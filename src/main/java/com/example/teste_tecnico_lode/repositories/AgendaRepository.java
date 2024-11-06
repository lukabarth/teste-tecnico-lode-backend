package com.example.teste_tecnico_lode.repositories;

import com.example.teste_tecnico_lode.models.AgendaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendaRepository extends JpaRepository<AgendaModel, Long> {
}
