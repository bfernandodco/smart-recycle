package br.com.fiap.fase5.capitulo4.coleta.controller;

import br.com.fiap.fase5.capitulo4.coleta.dto.CaminhaoDto;
import br.com.fiap.fase5.capitulo4.coleta.dto.CaminhaoExibicaoDto;
import br.com.fiap.fase5.capitulo4.coleta.service.CaminhaoService;
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

class CaminhaoControllerTest {
    @Mock
    private CaminhaoService caminhaoService;

    @InjectMocks
    private CaminhaoController caminhaoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscar() {
        String placa = "ABC1234";
        CaminhaoExibicaoDto caminhaoExibicaoDto = mock(CaminhaoExibicaoDto.class);
        when(caminhaoService.buscar(placa)).thenReturn(caminhaoExibicaoDto);

        ResponseEntity<CaminhaoExibicaoDto> response = caminhaoController.buscar(placa);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(caminhaoExibicaoDto, response.getBody());
        verify(caminhaoService, times(1)).buscar(placa);
    }

    @Test
    void testListar() {
        CaminhaoExibicaoDto caminhaoExibicaoDto = mock(CaminhaoExibicaoDto.class);
        List<CaminhaoExibicaoDto> mockList = Arrays.asList(caminhaoExibicaoDto, caminhaoExibicaoDto);
        when(caminhaoService.listar()).thenReturn(mockList);

        ResponseEntity<List<CaminhaoExibicaoDto>> response = caminhaoController.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockList, response.getBody());
        verify(caminhaoService, times(1)).listar();
    }

    @Test
    void testCadastrar() {
        CaminhaoDto caminhaoDto = mock(CaminhaoDto.class);
        doNothing().when(caminhaoService).cadastrar(caminhaoDto);

        ResponseEntity<Void> response = caminhaoController.cadastrar(caminhaoDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(caminhaoService, times(1)).cadastrar(caminhaoDto);
    }

    @Test
    void testAtualizar() {
        CaminhaoDto caminhaoDto = mock(CaminhaoDto.class);
        doNothing().when(caminhaoService).atualizar(caminhaoDto);

        ResponseEntity<Void> response = caminhaoController.atualizar(caminhaoDto);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        verify(caminhaoService, times(1)).atualizar(caminhaoDto);
    }

    @Test
    void testExcluir() {
        String placa = "ABC1234";
        doNothing().when(caminhaoService).excluir(placa);

        ResponseEntity<Void> response = caminhaoController.excluir(placa);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(caminhaoService, times(1)).excluir(placa);
    }
}