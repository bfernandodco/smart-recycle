package br.com.fiap.fase5.capitulo4.coleta.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "caminhoes")
public class Caminhao {

    @Id
    private String id;
    @DBRef
    private Rota rota;

    private Long capacidade;

    @Indexed(unique = true)
    private String placa;

    private Boolean statusServico;

    private String localizacaoEmTempoReal;

}
