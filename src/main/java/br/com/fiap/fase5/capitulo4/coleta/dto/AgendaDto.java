package br.com.fiap.fase5.capitulo4.coleta.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AgendaDto(
        String id,

        @NotNull(message = "A data da próxima coleta é obrigatória.")
        LocalDateTime dataProximaColeta,

        LocalDateTime dataUltimaColeta,

        @NotNull(message = "A rota é obrigatória.")
        String rota,

        @NotNull(message = "O ponto de coleta é obrigatório.")
        String pontoDeColeta
){

}
