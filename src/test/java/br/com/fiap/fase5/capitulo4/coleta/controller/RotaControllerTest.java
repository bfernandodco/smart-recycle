package br.com.fiap.fase5.capitulo4.coleta.controller;

import br.com.fiap.fase5.capitulo4.coleta.dto.RotaDto;
import br.com.fiap.fase5.capitulo4.coleta.service.RotaService;
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

class RotaControllerTest {

    @Mock
    private RotaService rotaService;

    @InjectMocks
    private RotaController rotaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGravar() {
        RotaDto rotaDto = mock(RotaDto.class);
        when(rotaService.gravar(rotaDto)).thenReturn(null);

        ResponseEntity<Void> response = rotaController.gravar(rotaDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(rotaService, times(1)).gravar(rotaDto);
    }

    @Test
    void testListar() {
        List<RotaDto> mockList = Arrays.asList(mock(RotaDto.class), mock(RotaDto.class));
        when(rotaService.listar()).thenReturn(mockList);

        ResponseEntity<List<RotaDto>> response = rotaController.listar();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockList, response.getBody());
        verify(rotaService, times(1)).listar();
    }

    @Test
    void testAtualizar() {
        RotaDto rotaDto = mock(RotaDto.class);
        doNothing().when(rotaService).atualizar(rotaDto);

        ResponseEntity<Void> response = rotaController.atualizar(rotaDto);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(rotaService, times(1)).atualizar(rotaDto);
    }

    @Test
    void testExcluir() {
        String id = "123";
        doNothing().when(rotaService).excluir(id);

        ResponseEntity<Void> response = rotaController.excluir(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(rotaService, times(1)).excluir(id);
    }
}