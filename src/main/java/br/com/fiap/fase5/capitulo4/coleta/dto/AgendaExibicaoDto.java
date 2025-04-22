package br.com.fiap.fase5.capitulo4.coleta.dto;

import java.time.LocalDateTime;

public record AgendaExibicaoDto(
        String id,

        LocalDateTime dataProximaColeta,

        LocalDateTime dataUltimaColeta,

        RotaDto rota,

        PontoDeColetaExibicaoDto pontoDeColeta
) {
}
