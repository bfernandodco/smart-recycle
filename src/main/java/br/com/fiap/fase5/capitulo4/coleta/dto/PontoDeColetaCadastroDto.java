package br.com.fiap.fase5.capitulo4.coleta.dto;

import br.com.fiap.fase5.capitulo4.coleta.model.Residuo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PontoDeColetaCadastroDto(

        String id,

        @NotBlank(message = "É preciso informar o endereço do ponto de coleta")
        String endereco,

        @NotNull(message = "É preciso informar a capacidade máxima.")
        BigDecimal capacidadeMaxima,

        BigDecimal capacidadeAtual,

        @NotNull(message = "Escolha o tipo de resíduo coletado.")
        Residuo residuo
) {
}
