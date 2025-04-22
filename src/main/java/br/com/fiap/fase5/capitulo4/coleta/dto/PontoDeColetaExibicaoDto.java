package br.com.fiap.fase5.capitulo4.coleta.dto;

import java.math.BigDecimal;

public record PontoDeColetaExibicaoDto(

        String id,
        String endereco,
        BigDecimal capacidadeMaxima,
        BigDecimal capacidadeAtual,
        String residuo
) {
}
