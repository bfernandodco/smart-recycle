package br.com.fiap.fase5.capitulo4.coleta.mapper;

import br.com.fiap.fase5.capitulo4.coleta.dto.CaminhaoDto;
import br.com.fiap.fase5.capitulo4.coleta.dto.CaminhaoExibicaoDto;
import br.com.fiap.fase5.capitulo4.coleta.model.Caminhao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CaminhaoMapper {

    CaminhaoMapper INSTANCE = Mappers.getMapper(CaminhaoMapper.class);

    @Mapping(target = "rota", ignore = true)
    Caminhao caminhaoDtoToCaminhao(CaminhaoDto caminhaoDto);

    CaminhaoExibicaoDto caminhaoToExibicaoDto(Caminhao caminhao);

    List<CaminhaoExibicaoDto> listaCaminhaoToListaExibicaoDto(List<Caminhao> caminhoes);
}
