package br.com.fiap.fase5.capitulo4.coleta.service;

import br.com.fiap.fase5.capitulo4.coleta.dto.UsuarioAtualizarDto;
import br.com.fiap.fase5.capitulo4.coleta.dto.UsuarioCadastroDto;
import br.com.fiap.fase5.capitulo4.coleta.dto.UsuarioExibicaoDto;
import br.com.fiap.fase5.capitulo4.coleta.mapper.UsuarioMapper;
import br.com.fiap.fase5.capitulo4.coleta.model.Role;
import br.com.fiap.fase5.capitulo4.coleta.model.Usuario;
import br.com.fiap.fase5.capitulo4.coleta.repository.UsuarioRepository;
import br.com.fiap.fase5.capitulo4.coleta.service.auth.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private AuthService authService;

    @Mock
    private UsuarioMapper mapper;

    @InjectMocks
    private UsuarioService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCadastrar() {
        UsuarioCadastroDto dto = new UsuarioCadastroDto(
                "1",
                "12345678900",
                "João Silva",
                "11999999999",
                "joao.silva@email.com",
                "senha123"
        );

        Usuario usuario = new Usuario();
        usuario.setId(dto.id());
        usuario.setCpf(dto.cpf());
        usuario.setNome(dto.nome());
        usuario.setTelefone(dto.telefone());
        usuario.setEmail(dto.email());
        usuario.setSenha("senhaCriptografada");
        usuario.setRole(Role.USER);

        UsuarioExibicaoDto exibicaoDto = new UsuarioExibicaoDto(
                dto.cpf(),
                dto.nome(),
                dto.telefone(),
                dto.email()
        );

        when(mapper.usuarioCadastroDtoToUsuario(dto)).thenReturn(usuario);
        when(authService.criptografarSenha(dto.senha())).thenReturn("senhaCriptografada");
        when(repository.save(usuario)).thenReturn(usuario);
        when(mapper.usuarioToUsuarioExibicaoDto(usuario)).thenReturn(exibicaoDto);

        UsuarioExibicaoDto result = service.cadastrar(dto);

        assertEquals(exibicaoDto, result);
        verify(repository, times(1)).save(usuario);
    }

    @Test
    void testCadastrarUsuarioJaCadastrado() {
        UsuarioCadastroDto dto = mock(UsuarioCadastroDto.class);
        when(repository.save(any(Usuario.class))).thenThrow(new IllegalArgumentException("Este usuário já está cadastrado."));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.cadastrar(dto));

        assertEquals("Este usuário já está cadastrado.", exception.getMessage());
    }

    @Test
    void testAtualizar() {
        UsuarioAtualizarDto dto = mock(UsuarioAtualizarDto.class);
        Usuario usuario = mock(Usuario.class);

        when(dto.cpf()).thenReturn("12345678900");
        when(repository.findUsuarioByCpf("12345678900")).thenReturn(usuario);

        service.atualizar(dto);

        verify(usuario).setEmail(dto.email());
        verify(usuario).setTelefone(dto.telefone());
        verify(repository, times(1)).save(usuario);
    }

    @Test
    void testAtualizarUsuarioNaoEncontrado() {
        UsuarioAtualizarDto dto = mock(UsuarioAtualizarDto.class);
        when(dto.cpf()).thenReturn("12345678900");
        when(repository.findUsuarioByCpf("12345678900")).thenThrow(new NullPointerException());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.atualizar(dto));

        assertEquals("Usuário não encontrado.", exception.getMessage());
    }

    @Test
    void testBuscar() {
        Usuario usuario = new Usuario();
        usuario.setId("1");
        usuario.setCpf("12345678900");
        usuario.setNome("João Silva");
        usuario.setTelefone("11999999999");
        usuario.setEmail("joao.silva@email.com");
        usuario.setSenha("senha123");
        usuario.setRole(Role.USER);

        UsuarioExibicaoDto exibicaoDto = new UsuarioExibicaoDto(
                usuario.getCpf(),
                usuario.getNome(),
                usuario.getTelefone(),
                usuario.getEmail()
        );

        when(repository.findUsuarioByCpf("12345678900")).thenReturn(usuario);
        when(mapper.usuarioToUsuarioExibicaoDto(usuario)).thenReturn(exibicaoDto);

        UsuarioExibicaoDto result = service.buscar("12345678900");

        assertEquals(exibicaoDto, result);
        verify(repository, times(1)).findUsuarioByCpf("12345678900");
    }

    @Test
    void testBuscarUsuarioNaoCadastrado() {
        when(repository.findUsuarioByCpf("12345678900")).thenThrow(new IllegalArgumentException("O CPF informado não está cadastrado."));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.buscar("12345678900"));

        assertEquals("O CPF informado não está cadastrado.", exception.getMessage());
    }

    @Test
    void testExcluir() {
        doNothing().when(repository).deleteUsuarioByCpf("12345678900");

        service.excluir("12345678900");

        verify(repository, times(1)).deleteUsuarioByCpf("12345678900");
    }

    @Test
    void testExcluirUsuarioNaoEncontrado() {
        doThrow(new IllegalArgumentException()).when(repository).deleteUsuarioByCpf("12345678900");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.excluir("12345678900"));

        assertEquals("Usuário não encontrado.", exception.getMessage());
    }
}