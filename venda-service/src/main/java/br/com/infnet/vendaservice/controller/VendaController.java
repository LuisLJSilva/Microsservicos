package br.com.infnet.vendaservice.controller;

import br.com.infnet.vendaservice.model.Produto;
import br.com.infnet.vendaservice.payload.VendaPayload;
import br.com.infnet.vendaservice.service.ProdutoService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class VendaController {
    @Autowired
    ProdutoService produtoService;

    Logger logger = LoggerFactory.getLogger(VendaController.class);
    @PostMapping
    @CircuitBreaker(name = "vendaService", fallbackMethod = "fallbackRegistraVenda")
    @Retry(name = "vendaService")
    @Operation(summary = "Registra uma venda",
            description = "Registra uma nova venda com base na lista de produtos fornecidos no payload.")
    @ApiResponse(responseCode = "200", description = "Venda registrada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = String.class),
                    examples = @ExampleObject(name = "Resposta de sucesso", value = "\"Venda registrada com sucesso!\"")))
    @ApiResponse(responseCode = "500", description = "Erro interno ao registrar a venda")
    @ApiResponse(responseCode = "503", description = "Serviço indisponível (fallback)")
    public ResponseEntity<?> registraVenda(@RequestBody VendaPayload vendaPayload) {
        try {
            List<Produto> allProdutos = produtoService.getAllProdutos(vendaPayload.getProdutos());
            return ResponseEntity.ok().body("Venda registrada com sucesso!" + allProdutos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao registrar venda");
        }
    }

    public ResponseEntity<?> fallbackRegistraVenda(VendaPayload vendaPayload, Throwable t) {
        logger.error("Fallback for registraVenda - Error: {}" , t.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Fallback response for registraVenda: " + t.getMessage());
    }
}
