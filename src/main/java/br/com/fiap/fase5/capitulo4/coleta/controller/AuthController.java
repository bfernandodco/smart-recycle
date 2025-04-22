package br.com.fiap.fase5.capitulo4.coleta.controller;

import br.com.fiap.fase5.capitulo4.coleta.dto.LoginDto;
import br.com.fiap.fase5.capitulo4.coleta.dto.UsuarioCadastroDto;
import br.com.fiap.fase5.capitulo4.coleta.dto.UsuarioExibicaoDto;
import br.com.fiap.fase5.capitulo4.coleta.model.Usuario;
import br.com.fiap.fase5.capitulo4.coleta.service.UsuarioService;
import br.com.fiap.fase5.capitulo4.coleta.service.auth.AuthService;
import br.com.fiap.fase5.capitulo4.coleta.service.auth.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoints de autenticação")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authManager;

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Realiza o login do usuário.")
    @ApiResponse(responseCode = "201", description = "Login realizado com sucesso.")
    @ApiResponse(responseCode = "400", description = "Usuário ou senha inválidos.")
    public ResponseEntity login(@RequestBody @Valid LoginDto dto) {
        UsernamePasswordAuthenticationToken usernamePassword
                = new UsernamePasswordAuthenticationToken(dto.cpf(), dto.senha());
        Authentication auth = authManager.authenticate(usernamePassword);
        String token = tokenService.gerarToken((Usuario) auth.getPrincipal());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/novo-admin")
    @ApiResponse(responseCode = "201", description = "Admin cadastrado com sucesso.")
    @ApiResponse(responseCode = "400", description = "Verifique as informações e tente novamente.")
    @Operation(summary = "Cadastrar novo admin", description = "Cadastra um novo usuário com perfil de admin.")
    public ResponseEntity<UsuarioExibicaoDto> cadastrar(@RequestBody @Valid UsuarioCadastroDto dto) {
        return new ResponseEntity<>(authService.cadastrarAdmin(dto), HttpStatus.CREATED);
    }

}
