package br.com.fiap.fase5.capitulo4.coleta.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Residuo {
    ORGANICO("Orgânico"),
    PAPEL("Papel"),
    VIDRO("Vidro"),
    METAL("Metal"),
    PLASTICO("Plástico"),
    COMUM("Comum"),
    INFECTANTE("Infectante"),
    ELETRONICO("Eletrônico");

    private String residuo;

    public static Residuo fromString(String residuoString) {
        try {
            for(Residuo residuo: Residuo.values()) {
                if(residuo.getResiduo().equals(residuoString)) {
                    return residuo;
                }
            }
        } catch(IllegalArgumentException e) {
            throw new RuntimeException("Tipo de resíduo inválido.");
        }
        return null;
    }
}
