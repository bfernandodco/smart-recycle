package br.com.fiap.fase5.capitulo4.coleta.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CaminhaoDto(

        String id,

        @NotBlank(message = "O id da rota é obrigatório.")
        String rota,

        @NotNull(message = "A capacidade é obrigatória.")
        Long capacidade,

        @NotBlank(message = "Placa é obrigatória.")
        String placa,

        @NotNull(message = "O status do serviço é obrigatório.")
        Boolean statusServico
) {
}
