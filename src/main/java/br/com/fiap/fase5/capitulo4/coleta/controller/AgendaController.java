package br.com.fiap.fase5.capitulo4.coleta.controller;

import br.com.fiap.fase5.capitulo4.coleta.dto.AgendaAtualizacaoDto;
import br.com.fiap.fase5.capitulo4.coleta.dto.AgendaDto;
import br.com.fiap.fase5.capitulo4.coleta.dto.AgendaExibicaoDto;
import br.com.fiap.fase5.capitulo4.coleta.service.AgendaService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/coleta")
@Tag(name = "Agenda", description = "Endpoints para agendamento de coletas.")
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    @PostMapping("/agenda/agendar-coleta")
    @ApiResponse(responseCode = "201", description = "Coleta agendada com sucesso.")
    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso.")
    public ResponseEntity<Void> agendar(@RequestBody @Valid AgendaDto dto) {
        agendaService.agendar(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/agenda/coletas-agendadas")
    @ApiResponse(responseCode = "200", description = "Lista de coletas agendadas:")
    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso.")
    public ResponseEntity<List<AgendaExibicaoDto>> listar() {
        return new ResponseEntity<>(agendaService.agendamentos(), HttpStatus.OK);
    }

    @PutMapping("/agenda/concluir-coleta")
    @ApiResponse(responseCode = "202", description = "Coleta concluída com sucesso.")
    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso.")
    public ResponseEntity<Void> concluir(@RequestBody AgendaAtualizacaoDto dto) {
        agendaService.concluir(dto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("agenda/suspender-coleta")
    @ApiResponse(responseCode = "202", description = "Coleta suspensa com sucesso.")
    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso.")
    public ResponseEntity<Void> suspender(@RequestBody @Valid String id) {
        agendaService.suspender(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("agenda/excluir-coleta-agendada/{id}")
    @ApiResponse(responseCode = "204", description = "Coleta agendada excluída com sucesso.")
    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso.")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        agendaService.excluir(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
