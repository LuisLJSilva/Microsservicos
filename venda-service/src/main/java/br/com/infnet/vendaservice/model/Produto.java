package br.com.infnet.vendaservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Modelo representando um Produto.")
public class Produto {
    @Schema(description = "ID único para o produto, gerado automaticamente.")
    private long id;
    @Schema(description = "Nome do produto, descrevendo-o brevemente.", example = "Kimono Keiko A2")
    private String nome;
    @Schema(description = "Valor monetário do produto.",
            example = "399.99",
            format = "decimal",
            minimum = "0",
            type = "number")
    private BigDecimal valor;
}
