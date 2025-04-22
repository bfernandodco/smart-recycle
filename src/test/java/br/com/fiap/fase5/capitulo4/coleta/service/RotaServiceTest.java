package br.com.fiap.fase5.capitulo4.coleta.service;

import br.com.fiap.fase5.capitulo4.coleta.dto.RotaDto;
import br.com.fiap.fase5.capitulo4.coleta.mapper.RotaMapper;
import br.com.fiap.fase5.capitulo4.coleta.model.Rota;
import br.com.fiap.fase5.capitulo4.coleta.repository.RotaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RotaServiceTest {

    @Mock
    private RotaRepository repository;

    @Mock
    private RotaMapper mapper;

    @InjectMocks
    private RotaService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGravarRotaJaCadastrada() {
        RotaDto dto = mock(RotaDto.class);
        when(repository.save(any(Rota.class))).thenThrow(new DataIntegrityViolationException("A rota informada já está cadastrada."));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.gravar(dto));

        assertEquals("A rota informada já está cadastrada.", exception.getMessage());
    }

    @Test
    void testBuscarRotaNaoCadastrada() {
        when(repository.findById("1")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.buscar("1"));

        assertEquals("A rota informada não está cadastrada.", exception.getMessage());
    }

    @Test
    void testAtualizarRotaNaoEncontrada() {
        RotaDto dto = mock(RotaDto.class);
        when(dto.id()).thenReturn("1");
        when(repository.findById("1")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.atualizar(dto));

        assertEquals("Rota não encontrada.", exception.getMessage());
    }

    @Test
    void testListar() {
        Rota rota1 = mock(Rota.class);
        Rota rota2 = mock(Rota.class);
        List<Rota> rotas = Arrays.asList(rota1, rota2);
        RotaDto dto1 = mock(RotaDto.class);
        RotaDto dto2 = mock(RotaDto.class);
        List<RotaDto> dtos = Arrays.asList(dto1, dto2);

        when(repository.findAll()).thenReturn(rotas);
        when(mapper.rotasToRotasDto(rotas)).thenReturn(dtos);

        List<RotaDto> result = service.listar();

        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testExcluir() {
        doNothing().when(repository).deleteById("1");

        service.excluir("1");

        verify(repository, times(1)).deleteById("1");
    }

    @Test
    void testExcluirRotaNaoEncontrada() {
        doThrow(new IllegalArgumentException()).when(repository).deleteById("1");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.excluir("1"));

        assertEquals("Rota não encontrada.", exception.getMessage());
    }
}