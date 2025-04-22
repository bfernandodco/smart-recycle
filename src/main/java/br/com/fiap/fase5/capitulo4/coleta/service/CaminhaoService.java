package br.com.fiap.fase5.capitulo4.coleta.service;

import br.com.fiap.fase5.capitulo4.coleta.dto.CaminhaoDto;
import br.com.fiap.fase5.capitulo4.coleta.dto.CaminhaoExibicaoDto;
import br.com.fiap.fase5.capitulo4.coleta.mapper.CaminhaoMapper;
import br.com.fiap.fase5.capitulo4.coleta.mapper.RotaMapper;
import br.com.fiap.fase5.capitulo4.coleta.model.Caminhao;
import br.com.fiap.fase5.capitulo4.coleta.repository.CaminhaoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CaminhaoService {

    @Autowired
    private CaminhaoRepository repository;

    @Autowired
    private RotaService rotaService;

    public void cadastrar(CaminhaoDto dto) {
        try {
            Caminhao caminhao = CaminhaoMapper.INSTANCE.caminhaoDtoToCaminhao(dto);
            caminhao.setRota(RotaMapper.INSTANCE.dtoToRota(rotaService.buscar(dto.rota())));
            repository.save(caminhao);
            log.info("Caminhão cadastrado com sucesso.");
        } catch(DataIntegrityViolationException e) {
            throw new RuntimeException("Erro. Caminhão já cadastrado.");
        }
    }

    public void atualizar(CaminhaoDto dto) {
        try {
            Caminhao caminhao = repository.findCaminhaoByPlaca(dto.placa());
            caminhao.setCapacidade(dto.capacidade());
            caminhao.setStatusServico(dto.statusServico());
            caminhao.setRota(RotaMapper.INSTANCE.dtoToRota(rotaService.buscar(dto.rota())));
            repository.save(caminhao);
            log.info("Caminhão atualizado com sucesso.");
        } catch(DataIntegrityViolationException e) {
            throw new RuntimeException("Caminhão não encontrado.");
        }
    }

    public CaminhaoExibicaoDto buscar(String placa) {
        try {
            Caminhao caminhao =  repository.findCaminhaoByPlaca(placa);
            return CaminhaoMapper.INSTANCE.caminhaoToExibicaoDto(caminhao);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Caminhão não encontrado");
        }
    }

    public List<CaminhaoExibicaoDto> listar() {
        List<Caminhao> caminhoes = repository.findAll();
        return CaminhaoMapper.INSTANCE.listaCaminhaoToListaExibicaoDto(caminhoes);
    }

    public void excluir(String placa) {
        try {
            repository.deleteCaminhaoByPlaca(placa);
            log.info("Caminhao excluído com sucesso.");
        } catch(DataIntegrityViolationException e) {
            throw new RuntimeException("Caminhão não encontrado.");
        }
    }

}
