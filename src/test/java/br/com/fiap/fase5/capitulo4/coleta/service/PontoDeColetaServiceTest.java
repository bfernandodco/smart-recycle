package br.com.fiap.fase5.capitulo4.coleta.service;

import br.com.fiap.fase5.capitulo4.coleta.dto.PontoDeColetaCadastroDto;
import br.com.fiap.fase5.capitulo4.coleta.dto.PontoDeColetaExibicaoDto;
import br.com.fiap.fase5.capitulo4.coleta.mapper.PontoDeColetaMapper;
import br.com.fiap.fase5.capitulo4.coleta.model.PontoDeColeta;
import br.com.fiap.fase5.capitulo4.coleta.repository.PontoDeColetaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PontoDeColetaServiceTest {

    @Mock
    private PontoDeColetaRepository repository;

    @Mock
    private PontoDeColetaMapper mapper;

    @InjectMocks
    private PontoDeColetaService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testListar() {
        PontoDeColeta ponto1 = mock(PontoDeColeta.class);
        PontoDeColeta ponto2 = mock(PontoDeColeta.class);
        List<PontoDeColeta> pontos = Arrays.asList(ponto1, ponto2);
        List<PontoDeColetaExibicaoDto> exibicaoDtos = Arrays.asList(mock(PontoDeColetaExibicaoDto.class), mock(PontoDeColetaExibicaoDto.class));

        when(repository.findAll()).thenReturn(pontos);
        when(mapper.listaExibicaoDtoToListaPontoDeColeta(pontos)).thenReturn(exibicaoDtos);

        List<PontoDeColetaExibicaoDto> result = service.listar();

        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testBuscarPontoDeColetaNaoEncontrado() {
        when(repository.findById("1")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.buscar("1"));

        assertEquals("Ponto de coleta não encontrado.", exception.getMessage());
    }

    @Test
    void testAtualizarPontoDeColetaNaoEncontrado() {
        PontoDeColetaCadastroDto dto = mock(PontoDeColetaCadastroDto.class);
        when(dto.id()).thenReturn("1");
        when(repository.findById("1")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.atualizar(dto));

        assertEquals("Ponto de coleta não encontrado.", exception.getMessage());
    }

    @Test
    void testExcluir() {
        doNothing().when(repository).deleteById("1");

        service.excluir("1");

        verify(repository, times(1)).deleteById("1");
    }

    @Test
    void testExcluirPontoDeColetaNaoEncontrado() {
        doThrow(new IllegalArgumentException()).when(repository).deleteById("1");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.excluir("1"));

        assertEquals("Ponto de coleta inexistente.", exception.getMessage());
    }

    @Test
    void testDefinirCapacidadeAtual() {
        BigDecimal capacidadeMaxima = BigDecimal.valueOf(100);
        BigDecimal capacidadeAtual = service.definirCapacidadeAtual(capacidadeMaxima);

        assertNotNull(capacidadeAtual);
        assertTrue(capacidadeAtual.compareTo(BigDecimal.ZERO) >= 0);
        assertTrue(capacidadeAtual.compareTo(capacidadeMaxima) <= 0);
    }
}