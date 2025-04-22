package br.com.fiap.fase5.capitulo4.coleta.controller;

import br.com.fiap.fase5.capitulo4.coleta.dto.AgendaAtualizacaoDto;
import br.com.fiap.fase5.capitulo4.coleta.dto.AgendaDto;
import br.com.fiap.fase5.capitulo4.coleta.dto.AgendaExibicaoDto;
import br.com.fiap.fase5.capitulo4.coleta.service.AgendaService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AgendaControllerTest {
    @Mock
    private AgendaService agendaService;

    @InjectMocks
    private AgendaController agendaController;

    public AgendaControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAgendar() {
        AgendaDto dto = mock(AgendaDto.class);
        doNothing().when(agendaService).agendar(dto);

        ResponseEntity<Void> response = agendaController.agendar(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(agendaService, times(1)).agendar(dto);
    }

    @Test
    void testListar() {
        AgendaExibicaoDto agendaExibicaoDto = mock(AgendaExibicaoDto.class);
        List<AgendaExibicaoDto> mockList = Arrays.asList(agendaExibicaoDto, agendaExibicaoDto);
        when(agendaService.agendamentos()).thenReturn(mockList);

        ResponseEntity<List<AgendaExibicaoDto>> response = agendaController.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockList, response.getBody());
        verify(agendaService, times(1)).agendamentos();
    }

    @Test
    void testConcluir() {
        AgendaAtualizacaoDto dto = mock(AgendaAtualizacaoDto.class);
        doNothing().when(agendaService).concluir(dto);

        ResponseEntity<Void> response = agendaController.concluir(dto);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        verify(agendaService, times(1)).concluir(dto);
    }

    @Test
    void testSuspender() {
        String id = "123";
        doNothing().when(agendaService).suspender(id);

        ResponseEntity<Void> response = agendaController.suspender(id);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        verify(agendaService, times(1)).suspender(id);
    }

    @Test
    void testExcluir() {
        String id = "123";
        doNothing().when(agendaService).excluir(id);

        ResponseEntity<Void> response = agendaController.excluir(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(agendaService, times(1)).excluir(id);
    }
}