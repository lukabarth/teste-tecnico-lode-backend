package com.example.teste_tecnico_lode.controllers;

import com.example.teste_tecnico_lode.dtos.AgendaRecordDto;
import com.example.teste_tecnico_lode.models.AgendaModel;
import com.example.teste_tecnico_lode.models.MedicoModel;
import com.example.teste_tecnico_lode.repositories.AgendaRepository;
import com.example.teste_tecnico_lode.repositories.MedicoRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@RestController
public class AgendaController {
    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    AgendaRepository agendaRepository;

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/agendas")
    public ResponseEntity<Object> saveAgenda(@RequestBody AgendaRecordDto agendaRecordDto) {
        var agendaModel = new AgendaModel();
        BeanUtils.copyProperties(agendaRecordDto, agendaModel);

        Optional<MedicoModel> medico = medicoRepository.findById(agendaRecordDto.medicoId());
        if (medico.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Médico não encontrado.");
        }
        agendaModel.setMedico(medico.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(agendaRepository.save(agendaModel));
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/agendas")
    public ResponseEntity<List<AgendaModel>> getAllAgendas() {
        return ResponseEntity.status(HttpStatus.OK).body(agendaRepository.findAll());
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/agendas/{id}")
    public ResponseEntity<Object> getOneAgenda(@PathVariable(value="id") Long id) {
        Optional<AgendaModel> agendaO = agendaRepository.findById(id);
        return agendaO.<ResponseEntity<Object>>map(agendaModel -> ResponseEntity.status(HttpStatus.OK).body(agendaModel)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agenda não encontrada"));
    }

    @CrossOrigin(origins = "http://localhost:5173")
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

    @CrossOrigin(origins = "http://localhost:5173")
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