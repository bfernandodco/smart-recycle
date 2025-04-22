package br.com.fiap.fase5.capitulo4.coleta.mapper;

import br.com.fiap.fase5.capitulo4.coleta.dto.AgendaExibicaoDto;
import br.com.fiap.fase5.capitulo4.coleta.model.Agenda;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AgendaMapper {

    AgendaMapper INSTANCE = Mappers.getMapper(AgendaMapper.class);

    AgendaExibicaoDto agendaToExibicaoDto(Agenda agenda);
    List<AgendaExibicaoDto> listaAgendaToListaExibicaoDto(List<Agenda> agendas);

}
