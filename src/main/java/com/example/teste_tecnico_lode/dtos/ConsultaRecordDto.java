package com.example.teste_tecnico_lode.dtos;

import java.time.LocalDate;
import java.util.List;

public record ConsultaRecordDto(LocalDate dataConsulta, Long agendaId, List<Long> pacienteId) {
}
