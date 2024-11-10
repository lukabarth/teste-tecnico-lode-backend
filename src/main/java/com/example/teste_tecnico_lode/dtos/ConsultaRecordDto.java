package com.example.teste_tecnico_lode.dtos;

import java.util.List;

public record ConsultaRecordDto(Long agendaId, List<Long> pacienteId) {
}
