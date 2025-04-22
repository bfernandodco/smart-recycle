package br.com.fiap.fase5.capitulo4.coleta.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "agendas")
public class Agenda {

    @Id
    private String id;

    private LocalDateTime dataProximaColeta;

    private LocalDateTime dataUltimaColeta;

    @DBRef
    private Rota rota;

    @DBRef
    private PontoDeColeta pontoDeColeta;

}
