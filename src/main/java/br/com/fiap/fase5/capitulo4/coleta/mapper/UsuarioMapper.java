package br.com.fiap.fase5.capitulo4.coleta.mapper;

import br.com.fiap.fase5.capitulo4.coleta.dto.UsuarioAtualizarDto;
import br.com.fiap.fase5.capitulo4.coleta.dto.UsuarioCadastroDto;
import br.com.fiap.fase5.capitulo4.coleta.dto.UsuarioExibicaoDto;
import br.com.fiap.fase5.capitulo4.coleta.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    Usuario atualizarDtoToUsuario(UsuarioAtualizarDto usuario);
    Usuario usuarioCadastroDtoToUsuario(UsuarioCadastroDto dto);
    UsuarioExibicaoDto usuarioToUsuarioExibicaoDto(Usuario usuario);
}
