package br.com.fiap.fase5.capitulo4.coleta.controller;

import br.com.fiap.fase5.capitulo4.coleta.dto.RotaDto;
import br.com.fiap.fase5.capitulo4.coleta.service.RotaService;
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
@Tag(name = "Rotas", description = "Endpoints para monitoramento de rotas.")
public class RotaController {

    @Autowired
    private RotaService rotaService;

    @PostMapping("/rotas/gravar")
    @ApiResponse(responseCode = "201", description = "Rota cadastrada com sucesso.")
    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso.")
    public ResponseEntity<Void> gravar(@RequestBody @Valid RotaDto dto) {
        rotaService.gravar(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/rotas/listar")
    @ApiResponse(responseCode = "200", description = "Rotas:")
    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso.")
    public ResponseEntity<List<RotaDto>> listar() {
        return new ResponseEntity<>(rotaService.listar(), HttpStatus.OK);
    }

    @PutMapping("/rotas/atualizar")
    @ApiResponse(responseCode = "203", description = "Rota atualizada com sucesso.")
    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso.")
    public ResponseEntity<Void> atualizar(@RequestBody @Valid RotaDto dto) {
        rotaService.atualizar(dto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/rotas/excluir/{id}")
    @ApiResponse(responseCode = "200", description = "Rota excluída com sucesso.")
    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso.")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        rotaService.excluir(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
