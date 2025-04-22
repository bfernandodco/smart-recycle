package br.com.fiap.fase5.capitulo4.coleta.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "rotas")
public class Rota {
    @Id
    private String id;

    @Indexed(unique = true)
    private String nome;

    private String inicio;

    private String fim;

}
