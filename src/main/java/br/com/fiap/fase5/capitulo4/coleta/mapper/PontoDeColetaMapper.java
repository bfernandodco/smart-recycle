package br.com.fiap.fase5.capitulo4.coleta.mapper;

import br.com.fiap.fase5.capitulo4.coleta.dto.PontoDeColetaCadastroDto;
import br.com.fiap.fase5.capitulo4.coleta.dto.PontoDeColetaExibicaoDto;
import br.com.fiap.fase5.capitulo4.coleta.model.PontoDeColeta;
import br.com.fiap.fase5.capitulo4.coleta.model.Residuo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PontoDeColetaMapper {

    PontoDeColetaMapper INSTANCE = Mappers.getMapper(PontoDeColetaMapper.class);

    @Mapping(source = "residuo", target = "residuo")
    PontoDeColeta cadastroDtoToPontoDeColeta(PontoDeColetaCadastroDto dto);

    @Mapping(source = "residuo", target = "residuo")
    PontoDeColeta exibicaoDtoToPontoDeColeta(PontoDeColetaExibicaoDto dto);

    @Mapping(source = "residuo", target = "residuo")
    PontoDeColetaExibicaoDto pontoDeColetaToExibicaoDto(PontoDeColeta pontoDeColeta);

    @Mapping(source = "residuo", target = "residuo")
    List<PontoDeColetaExibicaoDto> listaExibicaoDtoToListaPontoDeColeta(List<PontoDeColeta> pontosDeColeta);

    default Residuo stringToResiduo(String residuoString) {
        return Residuo.fromString(residuoString);
    }

    default String residuoToString(Residuo residuo) {
        return residuo.getResiduo();
    }
}
