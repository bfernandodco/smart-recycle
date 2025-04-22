package br.com.fiap.fase5.capitulo4.coleta.dto;

import jakarta.validation.constraints.NotBlank;

public record RotaDto(

        String id,

        @NotBlank(message = "O nome da rota é obrigatório.")
        String nome,

        @NotBlank(message = "O local onde a rota se inicia é obrigatório.")
        String inicio,

        @NotBlank(message = "O local onde a rota termina é obrigatório.")
        String fim
) {
}
