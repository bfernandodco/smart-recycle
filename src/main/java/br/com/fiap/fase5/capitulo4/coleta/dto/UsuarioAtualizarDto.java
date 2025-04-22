package br.com.fiap.fase5.capitulo4.coleta.dto;

import jakarta.validation.constraints.NotBlank;

public record UsuarioAtualizarDto(
        @NotBlank(message = "O campo cpf é obrigatório")
        String cpf,

        @NotBlank(message = "Informe um email válido")
        String email,

        @NotBlank(message = "Informe o número de telefone com DDD")
        String telefone
) {
}
