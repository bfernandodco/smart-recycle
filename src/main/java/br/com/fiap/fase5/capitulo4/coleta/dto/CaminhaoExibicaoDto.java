package br.com.fiap.fase5.capitulo4.coleta.dto;

import br.com.fiap.fase5.capitulo4.coleta.model.Rota;

public record CaminhaoExibicaoDto(
        String id,
        Rota rota,
        Long capacidade,
        String placa,
        Boolean statusServico
) {
}
