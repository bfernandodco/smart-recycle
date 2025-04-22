package br.com.fiap.fase5.capitulo4.coleta.service.auth;

import br.com.fiap.fase5.capitulo4.coleta.model.Usuario;
import br.com.fiap.fase5.capitulo4.coleta.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class TokenServiceTest {
    @Mock
    private AuthenticationManager authManager;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(tokenService, "jwtSecret", "testSecretKey");
    }

    @Test
    void testGerarToken() {
        Usuario usuario = new Usuario();
        usuario.setCpf("12345678900");

        String token = tokenService.gerarToken(usuario);

        assertNotNull(token);

        String issuer = com.auth0.jwt.JWT.decode(token).getIssuer();
        assertEquals("SmartRecicle", issuer);
    }

    @Test
    void testValidarToken() {
        Usuario usuario = new Usuario();
        usuario.setCpf("12345678900");

        String token = tokenService.gerarToken(usuario);
        String subject = tokenService.validarToken(token);

        assertEquals("12345678900", subject);
    }

    @Test
    void testValidarTokenThrowsException() {
        String invalidToken = "invalidToken";

        assertThrows(RuntimeException.class, () -> tokenService.validarToken(invalidToken));
    }

    @Test
    void testExpiracao() {
        Instant expiracao = tokenService.expiracao();

        assertNotNull(expiracao);
        assertTrue(expiracao.isAfter(Instant.now()));
    }

    @Test
    void testAlgoritmo() {
        assertNotNull(tokenService.algoritmo());
    }
}