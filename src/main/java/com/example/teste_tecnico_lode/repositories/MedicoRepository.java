package com.example.teste_tecnico_lode.repositories;

import com.example.teste_tecnico_lode.models.MedicoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<MedicoModel, Long> {

}
