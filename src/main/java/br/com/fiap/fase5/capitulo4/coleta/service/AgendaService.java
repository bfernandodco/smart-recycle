package br.com.fiap.fase5.capitulo4.coleta.service;

import br.com.fiap.fase5.capitulo4.coleta.dto.AgendaAtualizacaoDto;
import br.com.fiap.fase5.capitulo4.coleta.dto.AgendaDto;
import br.com.fiap.fase5.capitulo4.coleta.dto.AgendaExibicaoDto;
import br.com.fiap.fase5.capitulo4.coleta.mapper.AgendaMapper;
import br.com.fiap.fase5.capitulo4.coleta.mapper.PontoDeColetaMapper;
import br.com.fiap.fase5.capitulo4.coleta.mapper.RotaMapper;
import br.com.fiap.fase5.capitulo4.coleta.model.Agenda;
import br.com.fiap.fase5.capitulo4.coleta.repository.AgendaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class AgendaService {
    @Autowired
    private AgendaRepository repository;

    @Autowired
    private RotaService rotaService;

    @Autowired
    private PontoDeColetaService pontoDeColetaService;

    public void agendar(AgendaDto dto) {
        try {
            Agenda agenda = new Agenda();
            agenda.setRota(RotaMapper.INSTANCE.dtoToRota(rotaService.buscar(dto.rota())));
            agenda.setPontoDeColeta(PontoDeColetaMapper.INSTANCE.exibicaoDtoToPontoDeColeta(
                    pontoDeColetaService.buscar(dto.pontoDeColeta())));
            agenda.setDataProximaColeta(dto.dataProximaColeta());
            agenda.setDataUltimaColeta(LocalDateTime.now());
            repository.save(agenda);
            log.info("Coleta agendada com sucesso.");
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Ocorreu um erro ao agendar a coleta. Verifique os dados informados.");
        }
    }

    public List<AgendaExibicaoDto> agendamentos() {
        List<Agenda> agendas = repository.findAll();
        log.info("Coletas agendadas listadas com sucesso.", agendas);
        return AgendaMapper.INSTANCE.listaAgendaToListaExibicaoDto(agendas);
    }

    public void concluir(AgendaAtualizacaoDto dto) {
        try {
            Agenda agenda = repository.findById(dto.id()).orElseThrow(() -> new RuntimeException("Agenda não encontrada."));
            agenda.setDataUltimaColeta(LocalDateTime.now());
            agenda.setDataProximaColeta(LocalDateTime.of(1970, 01, 01, 00, 00));
            repository.save(agenda);
            log.info("Coleta concluída com sucesso.");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("A agenda é inválida. Não foi possível concluir a coleta.");
        }
    }

    public void suspender(String id) {
        Agenda agenda = repository.findById(id).orElseThrow(() -> new RuntimeException("Agenda não encontrada."));
        agenda.setDataProximaColeta(LocalDateTime.MIN);
        repository.save(agenda);
        log.info("Coleta suspensa com sucesso.");
    }

    public void excluir(String id) {
        try {
            repository.deleteById(id);
        } catch(DataIntegrityViolationException e) {
            throw new RuntimeException("Coleta excluída com sucesso.");
        }
    }

}
