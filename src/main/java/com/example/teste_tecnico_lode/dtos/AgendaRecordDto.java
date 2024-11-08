package com.example.teste_tecnico_lode.dtos;

import com.example.teste_tecnico_lode.models.DiaSemana;

import java.time.LocalTime;

public record AgendaRecordDto(DiaSemana diaSemana, LocalTime horarioDisponivel,
                              Long medicoId) {
}
