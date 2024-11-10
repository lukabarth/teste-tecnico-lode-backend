package com.example.teste_tecnico_lode.controllers;

import com.example.teste_tecnico_lode.dtos.PacienteRecordDto;
import com.example.teste_tecnico_lode.models.PacienteModel;
import com.example.teste_tecnico_lode.repositories.PacienteRepository;
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
class PacienteControllerTest {

    @InjectMocks
    private PacienteController pacienteController;

    @Mock
    private PacienteRepository pacienteRepository;

    @Test
    @DisplayName("Deve salvar um paciente com todos os dados corretos.")
    public void deveSalvarUmPacienteComDadosCorretos() {
        // Arrange
        PacienteRecordDto pacienteRecordDto = new PacienteRecordDto("Luka Barth", 24, "PR", "Maringá", "123456789-10", null);
        PacienteModel pacienteModel = new PacienteModel();
        pacienteModel.setNome("Luka Barth");
        pacienteModel.setIdade(24);
        pacienteModel.setEstado("PR");
        pacienteModel.setCidade("Maringá");
        pacienteModel.setCpf("123456789-10");
        pacienteModel.setConsultas(null);

        when(pacienteRepository.save(any(PacienteModel.class))).thenReturn(pacienteModel);

        // Act
        ResponseEntity<PacienteModel> response = pacienteController.savePaciente(pacienteRecordDto);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody(), "O corpo da resposta não deve ser nulo.");
        assertEquals("Luka Barth", response.getBody().getNome());
        assertEquals(24, response.getBody().getIdade());
        assertEquals("Maringá", response.getBody().getCidade());
        assertEquals("PR", response.getBody().getEstado());
        assertEquals("123456789-10", response.getBody().getCpf());

        verify(pacienteRepository, times(1)).save(any(PacienteModel.class));
    }

    @Test
    @DisplayName("Deve retornar lista de médicos cadastrados")
    public void deveRetornarListaDePacientes() {

        PacienteModel paciente1 = new PacienteModel();
        paciente1.setNome("Luka Barth");
        paciente1.setIdade(24);
        paciente1.setCidade("Maringá");
        paciente1.setEstado("PR");
        paciente1.setCpf("123456789-10");

        PacienteModel paciente2 = new PacienteModel();
        paciente2.setNome("Ana Souza");
        paciente2.setIdade(35);
        paciente2.setCidade("Faxinal");
        paciente2.setEstado("PR");
        paciente2.setCpf("123456787-15");

        List<PacienteModel> pacientes = Arrays.asList(paciente1, paciente2);

        when(pacienteRepository.findAll()).thenReturn(pacientes);

        // Act
        ResponseEntity<List<PacienteModel>> response = pacienteController.getAllPacientes();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody(), "O corpo da resposta não deve ser nulo.");
        assertEquals(2, response.getBody().size(), "A lista de pacientes deve ter 2 elementos.");
        assertEquals("Luka Barth", response.getBody().get(0).getNome(), "O nome do primeiro paciente deve ser 'Luka Barth'");
        assertEquals("Ana Souza", response.getBody().get(1).getNome(), "O nome do segundo paciente deve ser 'Ana Souza'");

        verify(pacienteRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar erro ao tentar buscar paciente inexistente")
    public void deveRetornarErroAoBuscarPacienteInexistente() {
        Long idInvalido = 999L;

        when(pacienteRepository.findById(idInvalido)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = pacienteController.getOnePaciente(idInvalido);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Paciente não encontrado.", response.getBody());
    }
}
