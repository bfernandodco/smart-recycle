package br.com.fiap.fase5.capitulo4.coleta.controller;

import br.com.fiap.fase5.capitulo4.coleta.dto.LoginDto;
import br.com.fiap.fase5.capitulo4.coleta.dto.UsuarioCadastroDto;
import br.com.fiap.fase5.capitulo4.coleta.dto.UsuarioExibicaoDto;
import br.com.fiap.fase5.capitulo4.coleta.model.Usuario;
import br.com.fiap.fase5.capitulo4.coleta.service.auth.AuthService;
import br.com.fiap.fase5.capitulo4.coleta.service.auth.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthControllerTest {
    @Mock
    private AuthService authService;

    @Mock
    private TokenService tokenService;

    @Mock
    private AuthenticationManager authManager;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin() {
        LoginDto loginDto = new LoginDto("12345678900", "senha123");
        Usuario usuario = new Usuario();
        String token = "mocked-token";

        Authentication authentication = mock(Authentication.class);
        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(usuario);
        when(tokenService.gerarToken(usuario)).thenReturn(token);

        ResponseEntity response = authController.login(loginDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(token, response.getBody());
        verify(authManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenService, times(1)).gerarToken(usuario);
    }

    @Test
    void testCadastrarNovoAdmin() {
        UsuarioCadastroDto cadastroDto = mock(UsuarioCadastroDto.class);
        UsuarioExibicaoDto exibicaoDto = mock(UsuarioExibicaoDto.class);

        when(authService.cadastrarAdmin(cadastroDto)).thenReturn(exibicaoDto);

        ResponseEntity<UsuarioExibicaoDto> response = authController.cadastrar(cadastroDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(exibicaoDto, response.getBody());
        verify(authService, times(1)).cadastrarAdmin(cadastroDto);
    }
}