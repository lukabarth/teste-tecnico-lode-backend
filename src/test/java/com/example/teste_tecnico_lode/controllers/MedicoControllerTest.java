package com.example.teste_tecnico_lode.controllers;

import com.example.teste_tecnico_lode.dtos.MedicoRecordDto;
import com.example.teste_tecnico_lode.models.MedicoModel;
import com.example.teste_tecnico_lode.repositories.MedicoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MedicoControllerTest {

    @InjectMocks
    private MedicoController medicoController;

    @Mock
    private MedicoRepository medicoRepository;

    @Test
    @DisplayName("Deve salvar um médico com todos os dados corretos.")
    public void deveSalvarUmMedico() {
        // Arrange
        MedicoRecordDto medicoRecordDto = new MedicoRecordDto("Luka Barth", "CRM", "PR");
        MedicoModel medicoModel = new MedicoModel();
        medicoModel.setNome("Luka Barth");
        medicoModel.setConselho("CRM");
        medicoModel.setEstado("PR");

        when(medicoRepository.save(any(MedicoModel.class))).thenReturn(medicoModel);

        // Act
        ResponseEntity<MedicoModel> response = medicoController.saveMedico(medicoRecordDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody(), "O corpo da resposta não deve ser nulo.");
        assertEquals("Luka Barth", response.getBody().getNome());
        assertEquals("CRM", response.getBody().getConselho());
        assertEquals("PR", response.getBody().getEstado());

        verify(medicoRepository, times(1)).save(any(MedicoModel.class));
    }

    @Test
    @DisplayName("Deve retornar lista de médicos cadastrados")
    public void deveRetornarListaDeMedicos() {

        MedicoModel medico1 = new MedicoModel();
        medico1.setNome("Luka Barth");
        medico1.setConselho("CRM");
        medico1.setEstado("PR");

        MedicoModel medico2 = new MedicoModel();
        medico2.setNome("Ana Souza");
        medico2.setConselho("CRM");
        medico2.setEstado("SP");

        List<MedicoModel> medicos = Arrays.asList(medico1, medico2);

        when(medicoRepository.findAll()).thenReturn(medicos);

        // Act
        ResponseEntity<List<MedicoModel>> response = medicoController.getAllMedicos();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody(), "O corpo da resposta não deve ser nulo.");
        assertEquals(2, response.getBody().size(), "A lista de médicos deve ter 2 elementos.");
        assertEquals("Luka Barth", response.getBody().get(0).getNome(), "O nome do primeiro médico deve ser 'Luka Barth'");
        assertEquals("Ana Souza", response.getBody().get(1).getNome(), "O nome do segundo médico deve ser 'Ana Souza'");

        verify(medicoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar erro ao tentar buscar médico inexistente")
    public void deveRetornarErroAoBuscarMedicoInexistente() {
        Long idInvalido = 999L;

        when(medicoRepository.findById(idInvalido)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = medicoController.getOneMedico(idInvalido);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Médico não encontrado.", response.getBody());
    }
}
