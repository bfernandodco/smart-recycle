package br.com.fiap.fase5.capitulo4.coleta.controller;

import br.com.fiap.fase5.capitulo4.coleta.dto.UsuarioAtualizarDto;
import br.com.fiap.fase5.capitulo4.coleta.dto.UsuarioCadastroDto;
import br.com.fiap.fase5.capitulo4.coleta.dto.UsuarioExibicaoDto;
import br.com.fiap.fase5.capitulo4.coleta.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCadastrar() {
        UsuarioCadastroDto cadastroDto = mock(UsuarioCadastroDto.class);
        UsuarioExibicaoDto exibicaoDto = mock(UsuarioExibicaoDto.class);

        when(usuarioService.cadastrar(cadastroDto)).thenReturn(exibicaoDto);

        ResponseEntity<UsuarioExibicaoDto> response = usuarioController.cadastrar(cadastroDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(exibicaoDto, response.getBody());
        verify(usuarioService, times(1)).cadastrar(cadastroDto);
    }

    @Test
    void testBuscar() {
        String cpf = "12345678900";
        UsuarioExibicaoDto exibicaoDto = mock(UsuarioExibicaoDto.class);

        when(usuarioService.buscar(cpf)).thenReturn(exibicaoDto);

        ResponseEntity<UsuarioExibicaoDto> response = usuarioController.buscar(cpf);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(exibicaoDto, response.getBody());
        verify(usuarioService, times(1)).buscar(cpf);
    }

    @Test
    void testAtualizar() {
        UsuarioAtualizarDto atualizarDto = mock(UsuarioAtualizarDto.class);
        doNothing().when(usuarioService).atualizar(atualizarDto);

        ResponseEntity<Void> response = usuarioController.atualizar(atualizarDto);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        verify(usuarioService, times(1)).atualizar(atualizarDto);
    }

    @Test
    void testExcluir() {
        String cpf = "12345678900";
        doNothing().when(usuarioService).excluir(cpf);

        ResponseEntity<Void> response = usuarioController.excluir(cpf);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(usuarioService, times(1)).excluir(cpf);
    }
}