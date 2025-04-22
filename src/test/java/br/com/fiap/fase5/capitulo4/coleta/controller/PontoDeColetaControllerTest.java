package br.com.fiap.fase5.capitulo4.coleta.controller;

import br.com.fiap.fase5.capitulo4.coleta.dto.PontoDeColetaCadastroDto;
import br.com.fiap.fase5.capitulo4.coleta.dto.PontoDeColetaExibicaoDto;
import br.com.fiap.fase5.capitulo4.coleta.service.PontoDeColetaService;
import org.junit.jupiter.api.BeforeEach;
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

class PontoDeColetaControllerTest {
    @Mock
    private PontoDeColetaService pontoDeColetaService;

    @InjectMocks
    private PontoDeColetaController pontoDeColetaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCadastrar() {
        PontoDeColetaCadastroDto dto = mock(PontoDeColetaCadastroDto.class);
        doNothing().when(pontoDeColetaService).cadastrar(dto);

        ResponseEntity<Void> response = pontoDeColetaController.cadastrar(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(pontoDeColetaService, times(1)).cadastrar(dto);
    }

    @Test
    void testListar() {
        PontoDeColetaExibicaoDto ponto = mock(PontoDeColetaExibicaoDto.class);
        List<PontoDeColetaExibicaoDto> mockList = Arrays.asList(ponto, ponto);
        when(pontoDeColetaService.listar()).thenReturn(mockList);

        ResponseEntity<List<PontoDeColetaExibicaoDto>> response = pontoDeColetaController.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockList, response.getBody());
        verify(pontoDeColetaService, times(1)).listar();
    }

    @Test
    void testAtualizar() {
        PontoDeColetaCadastroDto dto = mock(PontoDeColetaCadastroDto.class);
        doNothing().when(pontoDeColetaService).atualizar(dto);

        ResponseEntity<Void> response = pontoDeColetaController.atualizar(dto);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        verify(pontoDeColetaService, times(1)).atualizar(dto);
    }

    @Test
    void testExcluir() {
        String id = "123";
        doNothing().when(pontoDeColetaService).excluir(id);

        ResponseEntity<Void> response = pontoDeColetaController.excluir(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(pontoDeColetaService, times(1)).excluir(id);
    }
}