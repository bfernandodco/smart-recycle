package br.com.fiap.fase5.capitulo4.coleta.service.auth;

import br.com.fiap.fase5.capitulo4.coleta.dto.UsuarioCadastroDto;
import br.com.fiap.fase5.capitulo4.coleta.dto.UsuarioExibicaoDto;
import br.com.fiap.fase5.capitulo4.coleta.mapper.UsuarioMapper;
import br.com.fiap.fase5.capitulo4.coleta.model.Role;
import br.com.fiap.fase5.capitulo4.coleta.model.Usuario;
import br.com.fiap.fase5.capitulo4.coleta.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        return repository.findByCpf(cpf);
    }

    public String criptografarSenha(String senha) {
        return new BCryptPasswordEncoder().encode(senha);
    }

    public UsuarioExibicaoDto cadastrarAdmin(UsuarioCadastroDto dto) {
        try {
            Usuario usuario = UsuarioMapper.INSTANCE.usuarioCadastroDtoToUsuario(dto);
            usuario.setSenha(authService.criptografarSenha(dto.senha()));
            usuario.setRole(Role.ADMIN);
            repository.save(usuario);
            log.info("Usuário ADMIN cadastrado com sucesso.", usuario);
            return UsuarioMapper.INSTANCE.usuarioToUsuarioExibicaoDto(usuario);
        } catch(IllegalArgumentException e) {
            throw new RuntimeException("Usuário ADMIN já cadastrado.");
        }
    }

}
