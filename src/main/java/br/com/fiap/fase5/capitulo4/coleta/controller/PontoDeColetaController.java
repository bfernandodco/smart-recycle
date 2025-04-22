package br.com.fiap.fase5.capitulo4.coleta.controller;

import br.com.fiap.fase5.capitulo4.coleta.dto.PontoDeColetaCadastroDto;
import br.com.fiap.fase5.capitulo4.coleta.dto.PontoDeColetaExibicaoDto;
import br.com.fiap.fase5.capitulo4.coleta.service.PontoDeColetaService;
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
@Tag(name = "Pontos de Coleta", description = "Endpoints para gestão dos pontos de coleta.")
public class PontoDeColetaController {

    @Autowired
    private PontoDeColetaService pontoDeColetaService;

    @PostMapping("/pontos-de-coleta/cadastrar")
    @ApiResponse(responseCode = "201", description = "Ponto de coleta cadastrado com sucesso.")
    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso.")
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid PontoDeColetaCadastroDto dto) {
        pontoDeColetaService.cadastrar(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/pontos-de-coleta/listar")
    @ApiResponse(responseCode = "200", description = "Pontos de coleta:")
    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso.")
    public ResponseEntity<List<PontoDeColetaExibicaoDto>>listar() {
        return new ResponseEntity<>(pontoDeColetaService.listar(), HttpStatus.OK);
    }

    @PutMapping("/pontos-de-coleta/atualizar")
    @ApiResponse(responseCode = "203", description = "Ponto de coleta atualizado com sucesso.")
    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso.")
    public ResponseEntity<Void> atualizar(@RequestBody @Valid PontoDeColetaCadastroDto dto) {
        pontoDeColetaService.atualizar(dto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/pontos-de-coleta/excluir/{id}")
    @ApiResponse(responseCode = "200", description = "Ponto de coleta excluído com sucesso.")
    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso.")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        pontoDeColetaService.excluir(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
