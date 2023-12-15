package br.com.infnet.produtoservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Schema(description = "Modelo representando um Produto.")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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