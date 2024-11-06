package com.example.teste_tecnico_lode.controllers;

import com.example.teste_tecnico_lode.dtos.AgendaRecordDto;
import com.example.teste_tecnico_lode.models.AgendaModel;
import com.example.teste_tecnico_lode.repositories.AgendaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AgendaController {

    @Autowired
    AgendaRepository agendaRepository;

    @PostMapping("/agendas")
    public ResponseEntity<AgendaModel> saveAgenda(@RequestBody AgendaRecordDto agendaRecordDto) {
        var agendaModel = new AgendaModel();
        BeanUtils.copyProperties(agendaRecordDto, agendaModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(agendaRepository.save(agendaModel));
    }

    @GetMapping("/agendas")
    public ResponseEntity<List<AgendaModel>> getAllAgendas() {
        return ResponseEntity.status(HttpStatus.OK).body(agendaRepository.findAll());
    }

    @GetMapping("/agendas/{id}")
    public ResponseEntity<Object> getOneAgenda(@PathVariable(value="id") Long id) {
        Optional<AgendaModel> agendaO = agendaRepository.findById(id);
        return agendaO.<ResponseEntity<Object>>map(agendaModel -> ResponseEntity.status(HttpStatus.OK).body(agendaModel)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agenda não encontrada"));
    }

    @PutMapping("agenda/{id}")
    public ResponseEntity<Object> updateAgenda(@PathVariable(value = "id") Long id,
                                               @RequestBody AgendaRecordDto agendaRecordDto) {
        Optional<AgendaModel> agendaO = agendaRepository.findById(id);
        if (agendaO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agenda não encontrado.");
        }
        var agendaModel = agendaO.get();
        BeanUtils.copyProperties(agendaRecordDto, agendaModel);
        return ResponseEntity.status(HttpStatus.OK).body(agendaRepository.save(agendaModel));
    }

    @DeleteMapping("agenda/{id}")
    public ResponseEntity<Object> deleteAgenda(@PathVariable(value = "id") Long id) {
        Optional<AgendaModel> agendaO = agendaRepository.findById(id);
        if(agendaO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agenda não encontrado");
        }
        agendaRepository.delete(agendaO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Agenda excluída com sucesso.");
    }
}