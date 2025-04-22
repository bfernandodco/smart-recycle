package br.com.fiap.fase5.capitulo4.coleta.controller;

import br.com.fiap.fase5.capitulo4.coleta.config.security.SecurityConfig;
import br.com.fiap.fase5.capitulo4.coleta.dto.UsuarioAtualizarDto;
import br.com.fiap.fase5.capitulo4.coleta.dto.UsuarioCadastroDto;
import br.com.fiap.fase5.capitulo4.coleta.dto.UsuarioExibicaoDto;
import br.com.fiap.fase5.capitulo4.coleta.service.UsuarioService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/coleta")
@Tag(name = "Usuário", description = "Endpoints para monitoramento de rotas.")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class UsuarioController {
    @Autowired
    private UsuarioService service;

    @PostMapping("/usuario/cadastro")
    @ApiResponse(responseCode = "200", description = "Usuário excluído com sucesso.")
    @ApiResponse(responseCode = "400", description = "Verifique os dados informados.")
    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso.")
    public ResponseEntity<UsuarioExibicaoDto> cadastrar(@RequestBody @Valid UsuarioCadastroDto dto) {
        return new ResponseEntity<>(service.cadastrar(dto), HttpStatus.CREATED);
    }

    @ApiResponse(responseCode = "200", description = "Usuário:")
    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso.")
    @GetMapping("/usuario/buscar-usuario/{cpf}")
    public ResponseEntity<UsuarioExibicaoDto> buscar(@PathVariable String cpf) {
        return new ResponseEntity<>(service.buscar(cpf), HttpStatus.OK);
    }

    @ApiResponse(responseCode = "203", description = "Usuário atualizada com sucesso.")
    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso.")
    @PutMapping("/usuario/atualizar-dados")
    public ResponseEntity<Void> atualizar(@RequestBody @Valid UsuarioAtualizarDto dto) {
        service.atualizar(dto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("usuario/excluir-usuario/{cpf}")
    @ApiResponse(responseCode = "200", description = "Usuário excluída com sucesso.")
    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso.")
    public ResponseEntity<Void> excluir(@PathVariable String cpf) {
        service.excluir(cpf);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
