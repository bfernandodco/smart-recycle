package br.com.fiap.fase5.capitulo4.coleta.mapper;

import br.com.fiap.fase5.capitulo4.coleta.dto.RotaDto;
import br.com.fiap.fase5.capitulo4.coleta.model.Rota;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RotaMapper {

    RotaMapper INSTANCE = Mappers.getMapper(RotaMapper.class);

    Rota dtoToRota(RotaDto dto);
    RotaDto rotaToDto(Rota rota);
    List<RotaDto> rotasToRotasDto(List<Rota> rotas);
}
