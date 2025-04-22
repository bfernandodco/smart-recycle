package br.com.fiap.fase5.capitulo4.coleta.service.auth;

import br.com.fiap.fase5.capitulo4.coleta.dto.UsuarioCadastroDto;
import br.com.fiap.fase5.capitulo4.coleta.dto.UsuarioExibicaoDto;
import br.com.fiap.fase5.capitulo4.coleta.mapper.UsuarioMapper;
import br.com.fiap.fase5.capitulo4.coleta.model.Role;
import br.com.fiap.fase5.capitulo4.coleta.model.Usuario;
import br.com.fiap.fase5.capitulo4.coleta.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {
    @Mock
    private UsuarioRepository repository;

    @Mock
    private UsuarioMapper mapper;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername() {
        Usuario usuario = new Usuario();
        usuario.setCpf("12345678900");

        when(repository.findByCpf("12345678900")).thenReturn(usuario);

        UserDetails result = authService.loadUserByUsername("12345678900");

        assertEquals(usuario, result);
        verify(repository, times(1)).findByCpf("12345678900");
    }

    @Test
    void testCriptografarSenha() {
        String senha = "senha123";
        String senhaCriptografada = authService.criptografarSenha(senha);

        assertNotNull(senhaCriptografada);
        assertTrue(new BCryptPasswordEncoder().matches(senha, senhaCriptografada));
    }

    @Test
    void testCadastrarAdmin() {
        UsuarioCadastroDto dto = new UsuarioCadastroDto(
                "1",
                "12345678900",
                "Admin User",
                "11999999999",
                "admin@email.com",
                "senha123"
        );

        Usuario usuario = new Usuario();
        usuario.setCpf(dto.cpf());
        usuario.setNome(dto.nome());
        usuario.setTelefone(dto.telefone());
        usuario.setEmail(dto.email());
        usuario.setSenha("senhaCriptografada");
        usuario.setRole(Role.ADMIN);

        UsuarioExibicaoDto exibicaoDto = new UsuarioExibicaoDto(
                dto.cpf(),
                dto.nome(),
                dto.telefone(),
                dto.email()
        );

        when(repository.save(any(Usuario.class))).thenReturn(usuario);
        when(mapper.usuarioToUsuarioExibicaoDto(any(Usuario.class))).thenReturn(exibicaoDto);

        UsuarioExibicaoDto result = authService.cadastrarAdmin(dto);

        assertEquals(exibicaoDto, result);

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(repository).save(captor.capture());

        Usuario savedUsuario = captor.getValue();
        assertEquals(dto.cpf(), savedUsuario.getCpf());
        assertEquals(dto.nome(), savedUsuario.getNome());
        assertEquals(dto.telefone(), savedUsuario.getTelefone());
        assertEquals(dto.email(), savedUsuario.getEmail());
        assertEquals(Role.ADMIN, savedUsuario.getRole());
        assertNotNull(savedUsuario.getSenha()); // Garantir que a senha foi criptografada
    }
}