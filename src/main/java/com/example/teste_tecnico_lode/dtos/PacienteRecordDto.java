package com.example.teste_tecnico_lode.dtos;

import java.util.List;

public record PacienteRecordDto(String nome, int idade,
                                String estado, String cidade, String cpf,
                                List<Long> consultaId) {
}
