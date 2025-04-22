package br.com.fiap.fase5.capitulo4.coleta.controller;

import br.com.fiap.fase5.capitulo4.coleta.dto.CaminhaoDto;
import br.com.fiap.fase5.capitulo4.coleta.dto.CaminhaoExibicaoDto;
import br.com.fiap.fase5.capitulo4.coleta.service.CaminhaoService;
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
@Tag(name = "Caminhões", description = "Endpoints para gerenciamento dos caminhões de coleta.")
public class CaminhaoController {
    @Autowired
    private CaminhaoService service;

    @GetMapping("/caminhoes/buscar/{placa}")
    @ApiResponse(responseCode = "200", description = "Caminhão encontrado:")
    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso.")
    public ResponseEntity<CaminhaoExibicaoDto> buscar(@PathVariable String placa) {
        return new ResponseEntity<>(service.buscar(placa), HttpStatus.OK);
    }

    @GetMapping("/caminhoes/listar")
    @ApiResponse(responseCode = "200", description = "Lista de caminhões")
    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso.")
    public ResponseEntity<List<CaminhaoExibicaoDto>> listar() {
        return new ResponseEntity<>(service.listar(), HttpStatus.OK);
    }

    @PostMapping("/caminhoes/cadastrar-caminhao")
    @ApiResponse(responseCode = "201", description = "Caminhão cadastrado com sucesso.")
    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso.")
    public ResponseEntity<Void> cadastrar(@Valid @RequestBody CaminhaoDto dto) {
        service.cadastrar(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/caminhoes/atualizar-caminhao")
    @ApiResponse(responseCode = "203", description = "Caminhão atualizado com sucesso.")
    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso.")
    public ResponseEntity<Void> atualizar(@Valid @RequestBody CaminhaoDto dto) {
        service.atualizar(dto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/caminhoes/excluir-caminhao/{placa}")
    @ApiResponse(responseCode = "204", description = "Caminhão excluído com sucesso.")
    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso.")
    public ResponseEntity<Void> excluir(@PathVariable String placa) {
        service.excluir(placa);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}