package br.com.fiap.fase5.capitulo4.coleta.service;

import br.com.fiap.fase5.capitulo4.coleta.dto.UsuarioAtualizarDto;
import br.com.fiap.fase5.capitulo4.coleta.dto.UsuarioCadastroDto;
import br.com.fiap.fase5.capitulo4.coleta.dto.UsuarioExibicaoDto;
import br.com.fiap.fase5.capitulo4.coleta.mapper.UsuarioMapper;
import br.com.fiap.fase5.capitulo4.coleta.model.Role;
import br.com.fiap.fase5.capitulo4.coleta.model.Usuario;
import br.com.fiap.fase5.capitulo4.coleta.repository.UsuarioRepository;
import br.com.fiap.fase5.capitulo4.coleta.service.auth.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private AuthService authService;

    public UsuarioExibicaoDto cadastrar(UsuarioCadastroDto dto) {
        try {
            Usuario usuario = UsuarioMapper.INSTANCE.usuarioCadastroDtoToUsuario(dto);
            usuario.setSenha(authService.criptografarSenha(dto.senha()));
            usuario.setRole(Role.USER);
            repository.save(usuario);
            log.info("Usuário cadastrado com sucesso.", usuario);
            return UsuarioMapper.INSTANCE.usuarioToUsuarioExibicaoDto(usuario);
        } catch(IllegalArgumentException e) {
            throw new RuntimeException("Este usuário já está cadastrado.");
        }
    }

    public void atualizar(UsuarioAtualizarDto dto) {
        try {
            Usuario usuario = repository.findUsuarioByCpf(dto.cpf());
            usuario.setEmail(dto.email());
            usuario.setTelefone(dto.telefone());
            repository.save(usuario);
            log.info("Usuário atualizado com sucesso.");
        } catch(DataIntegrityViolationException | NullPointerException e) {
            throw new RuntimeException("Usuário não encontrado.");
        }
    }

    public UsuarioExibicaoDto buscar(String cpf) {
        try {
            return UsuarioMapper.INSTANCE.usuarioToUsuarioExibicaoDto(repository.findUsuarioByCpf(cpf));
        } catch(IllegalArgumentException e) {
            throw new RuntimeException("O CPF informado não está cadastrado.");
        }
    }

    public void excluir(String cpf) {
        try {
            repository.deleteUsuarioByCpf(cpf);
        } catch(IllegalArgumentException e) {
            throw new RuntimeException("Usuário não encontrado.");
        }
    }

}
