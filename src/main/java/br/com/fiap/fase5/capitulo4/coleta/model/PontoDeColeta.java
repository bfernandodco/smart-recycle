package br.com.fiap.fase5.capitulo4.coleta.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Document(collection = "pontosDeColeta")
public class PontoDeColeta {

    @Id
    private String id;

    private String endereco;

    private BigDecimal capacidadeMaxima;

    private BigDecimal capacidadeAtual;

    private String residuo;

}