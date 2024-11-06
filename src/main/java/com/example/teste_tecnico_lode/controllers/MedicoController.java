package com.example.teste_tecnico_lode.controllers;

import com.example.teste_tecnico_lode.dtos.MedicoRecordDto;
import com.example.teste_tecnico_lode.models.MedicoModel;
import com.example.teste_tecnico_lode.repositories.MedicoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MedicoController {

    @Autowired
    MedicoRepository medicoRepository;

    @PostMapping("/medicos")
    public ResponseEntity<MedicoModel> saveMedico(@RequestBody MedicoRecordDto medicoRecordDto) {
        var medicoModel = new MedicoModel();
        BeanUtils.copyProperties(medicoRecordDto, medicoModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(medicoRepository.save(medicoModel));
    }

    @GetMapping("/medicos")
    public ResponseEntity<List<MedicoModel>> getAllMedicos() {
        return ResponseEntity.status(HttpStatus.OK).body(medicoRepository.findAll());
    }

    @GetMapping("/medicos/{id}")
    public ResponseEntity<Object> getOneMedico(@PathVariable(value = "id") Long id) {
        Optional<MedicoModel> medicoO = medicoRepository.findById(id);
        return medicoO.<ResponseEntity<Object>>map(medicoModel -> ResponseEntity.status(HttpStatus.OK).body(medicoModel)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Médico não encontrado."));
    }

    @PutMapping("medico/{id}")
    public ResponseEntity<Object> updateMedico(@PathVariable(value = "id") Long id,
                                               @RequestBody MedicoRecordDto medicoRecordDto) {
        Optional<MedicoModel> medicoO = medicoRepository.findById(id);
        if (medicoO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Médico não encontrado.");
        }
        var medicoModel = medicoO.get();
        BeanUtils.copyProperties(medicoRecordDto, medicoModel);
        return ResponseEntity.status(HttpStatus.OK).body(medicoRepository.save(medicoModel));
    }

    @DeleteMapping("medico/{id}")
    public ResponseEntity<Object> deleteMedico(@PathVariable(value = "id") Long id) {
        Optional<MedicoModel> medicoO = medicoRepository.findById(id);
        if(medicoO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Médico não encontrado");
        }
        medicoRepository.delete(medicoO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Médico excluído com sucesso.");
    }
}
