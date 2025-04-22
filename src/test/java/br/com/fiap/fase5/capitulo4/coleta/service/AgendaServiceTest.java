package br.com.fiap.fase5.capitulo4.coleta.service;

import br.com.fiap.fase5.capitulo4.coleta.dto.AgendaAtualizacaoDto;
import br.com.fiap.fase5.capitulo4.coleta.dto.AgendaDto;
import br.com.fiap.fase5.capitulo4.coleta.model.Agenda;
import br.com.fiap.fase5.capitulo4.coleta.repository.AgendaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AgendaServiceTest {

    @Mock
    private AgendaRepository repository;

    @Mock
    private RotaService rotaService;

    @Mock
    private PontoDeColetaService pontoDeColetaService;

    @InjectMocks
    private AgendaService agendaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAgendar() {
        AgendaDto dto = mock(AgendaDto.class);
        Agenda agenda = mock(Agenda.class);

        when(rotaService.buscar(dto.rota())).thenReturn(null);
        when(pontoDeColetaService.buscar(dto.pontoDeColeta())).thenReturn(null);
        when(repository.save(any(Agenda.class))).thenReturn(agenda);

        agendaService.agendar(dto);

        verify(repository, times(1)).save(any(Agenda.class));
    }

    @Test
    void testConcluir() {
        AgendaAtualizacaoDto dto = mock(AgendaAtualizacaoDto.class);
        Agenda agenda = mock(Agenda.class);

        when(dto.id()).thenReturn("1");
        when(repository.findById("1")).thenReturn(Optional.of(agenda));

        agendaService.concluir(dto);

        verify(agenda).setDataUltimaColeta(any(LocalDateTime.class));
        verify(agenda).setDataProximaColeta(any(LocalDateTime.class));
        verify(repository, times(1)).save(agenda);
    }

    @Test
    void testConcluirAgendaNaoEncontrada() {
        AgendaAtualizacaoDto dto = mock(AgendaAtualizacaoDto.class);
        when(dto.id()).thenReturn("1");
        when(repository.findById("1")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> agendaService.concluir(dto));
        assertEquals("Agenda não encontrada.", exception.getMessage());
    }

    @Test
    void testSuspender() {
        Agenda agenda = mock(Agenda.class);
        when(repository.findById("1")).thenReturn(Optional.of(agenda));

        agendaService.suspender("1");

        verify(agenda).setDataProximaColeta(LocalDateTime.MIN);
        verify(repository, times(1)).save(agenda);
    }

    @Test
    void testSuspenderAgendaNaoEncontrada() {
        when(repository.findById("1")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> agendaService.suspender("1"));
        assertEquals("Agenda não encontrada.", exception.getMessage());
    }

    @Test
    void testExcluir() {
        doNothing().when(repository).deleteById("1");

        agendaService.excluir("1");

        verify(repository, times(1)).deleteById("1");
    }
}