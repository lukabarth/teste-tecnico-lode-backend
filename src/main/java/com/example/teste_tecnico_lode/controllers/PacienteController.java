package com.example.teste_tecnico_lode.controllers;

import com.example.teste_tecnico_lode.dtos.PacienteRecordDto;
import com.example.teste_tecnico_lode.models.PacienteModel;
import com.example.teste_tecnico_lode.repositories.PacienteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PacienteController {

    @Autowired
    PacienteRepository pacienteRepository;

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/pacientes")
    public ResponseEntity<PacienteModel> savePaciente(@RequestBody PacienteRecordDto pacienteRecordDto) {
        var pacienteModel = new PacienteModel();
        BeanUtils.copyProperties(pacienteRecordDto, pacienteModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteRepository.save(pacienteModel));
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/pacientes")
    public ResponseEntity<List<PacienteModel>> getAllPacientes() {
        return ResponseEntity.status(HttpStatus.OK).body(pacienteRepository.findAll());
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/pacientes/{id}")
    public ResponseEntity<Object> getOnePaciente(@PathVariable(value = "id") Long id) {
        Optional<PacienteModel> pacienteO = pacienteRepository.findById(id);
        return pacienteO.<ResponseEntity<Object>>map(pacienteModel -> ResponseEntity.status(HttpStatus.OK).body(pacienteModel)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado."));
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PutMapping("pacientes/{id}")
    public ResponseEntity<Object> updatePaciente(@PathVariable(value = "id") Long id,
                                               @RequestBody PacienteRecordDto pacienteRecordDto) {
        Optional<PacienteModel> pacienteO = pacienteRepository.findById(id);
        if (pacienteO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado.");
        }
        var pacienteModel = pacienteO.get();
        BeanUtils.copyProperties(pacienteRecordDto, pacienteModel);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteRepository.save(pacienteModel));
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @DeleteMapping("pacientes/{id}")
    public ResponseEntity<Object> deletePaciente(@PathVariable(value = "id") Long id) {
        Optional<PacienteModel> pacienteO = pacienteRepository.findById(id);
        if(pacienteO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado");
        }
        pacienteRepository.delete(pacienteO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Paciente excluído com sucesso.");
    }
}
