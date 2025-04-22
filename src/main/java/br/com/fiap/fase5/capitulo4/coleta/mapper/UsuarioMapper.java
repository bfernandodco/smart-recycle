package br.com.fiap.fase5.capitulo4.coleta.mapper;

import br.com.fiap.fase5.capitulo4.coleta.dto.UsuarioAtualizarDto;
import br.com.fiap.fase5.capitulo4.coleta.dto.UsuarioCadastroDto;
import br.com.fiap.fase5.capitulo4.coleta.dto.UsuarioExibicaoDto;
import br.com.fiap.fase5.capitulo4.coleta.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", constant = "USER")
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "senha", ignore = true)
    @Mapping(target = "nome", ignore = true)
    Usuario atualizarDtoToUsuario(UsuarioAtualizarDto usuario);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", constant = "USER")
    @Mapping(target = "authorities", ignore = true)
    Usuario usuarioCadastroDtoToUsuario(UsuarioCadastroDto dto);

    UsuarioExibicaoDto usuarioToUsuarioExibicaoDto(Usuario usuario);
}
