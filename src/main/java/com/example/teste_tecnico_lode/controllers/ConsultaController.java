package com.example.teste_tecnico_lode.controllers;

import com.example.teste_tecnico_lode.dtos.ConsultaRecordDto;
import com.example.teste_tecnico_lode.models.AgendaModel;
import com.example.teste_tecnico_lode.models.ConsultaModel;
import com.example.teste_tecnico_lode.models.PacienteModel;
import com.example.teste_tecnico_lode.repositories.AgendaRepository;
import com.example.teste_tecnico_lode.repositories.ConsultaRepository;
import com.example.teste_tecnico_lode.repositories.PacienteRepository;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Setter
@RestController
public class ConsultaController {
    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    ConsultaRepository consultaRepository;

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/consultas")
    public ResponseEntity<Object> saveConsulta(@RequestBody ConsultaRecordDto consultaRecordDto) {
        var consultaModel = new ConsultaModel();
        BeanUtils.copyProperties(consultaRecordDto, consultaModel);

        Optional<AgendaModel> agenda = agendaRepository.findById(consultaRecordDto.agendaId());
        if (agenda.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agenda não encontrada.");
        }
        consultaModel.setAgenda(agenda.get());

        List<PacienteModel> pacientes = new ArrayList<>();
        for (Long pacienteId : consultaRecordDto.pacienteId()) {
            Optional<PacienteModel> paciente = pacienteRepository.findById(pacienteId);
            if (paciente.isPresent()) {
                pacientes.add(paciente.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente com ID " + pacienteId + " não encontrado.");
            }
        }
        consultaModel.setPacientes(pacientes);

        return ResponseEntity.status(HttpStatus.CREATED).body(consultaRepository.save(consultaModel));
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/consultas")
    public ResponseEntity<List<ConsultaModel>> getAllConsultas() {
        return ResponseEntity.status(HttpStatus.OK).body(consultaRepository.findAll());
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/consultas/{id}")
    public ResponseEntity<Object> getOneConsulta(@PathVariable(value="id") Long id) {
        Optional<ConsultaModel> consultaO = consultaRepository.findById(id);
        return consultaO.<ResponseEntity<Object>>map(consultaModel -> ResponseEntity.status(HttpStatus.OK).body(consultaModel)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consulta não encontrada"));
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PutMapping("consulta/{id}")
    public ResponseEntity<Object> updateConsulta(@PathVariable(value = "id") Long id,
                                               @RequestBody ConsultaRecordDto consultaRecordDto) {
        Optional<ConsultaModel> consultaO = consultaRepository.findById(id);
        if (consultaO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consulta não encontrado.");
        }
        var consultaModel = consultaO.get();
        BeanUtils.copyProperties(consultaRecordDto, consultaModel);
        return ResponseEntity.status(HttpStatus.OK).body(consultaRepository.save(consultaModel));
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @DeleteMapping("consulta/{id}")
    public ResponseEntity<Object> deleteConsulta(@PathVariable(value = "id") Long id) {
        Optional<ConsultaModel> consultaO = consultaRepository.findById(id);
        if(consultaO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consulta não encontrado");
        }
        consultaRepository.delete(consultaO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Consulta excluída com sucesso.");
    }
}