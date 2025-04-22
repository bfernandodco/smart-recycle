package br.com.fiap.fase5.capitulo4.coleta.dto;

import java.time.LocalDateTime;

public record ErrorDto(
    LocalDateTime timestamp,
    Integer status,
    String error,
    String message,
    String path
) {
}
