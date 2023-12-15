package br.com.infnet.produtoservice.controller;

import br.com.infnet.produtoservice.exception.ProdutoNotFoundException;
import br.com.infnet.produtoservice.model.Produto;
import br.com.infnet.produtoservice.payload.ResponsePayload;
import br.com.infnet.produtoservice.service.ProdutoService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/")
public class ProdutoController {
    @Value("${spring.application.serverNick}")
    private String nickName;
    @Autowired
    ProdutoService produtoService;

    Logger LOGGER = LoggerFactory.getLogger(ProdutoController.class);
    @GetMapping
    @CircuitBreaker(name = "produtoService", fallbackMethod = "fallbackGetAll")
    @Retry(name = "produtoService")
    @Operation(summary = "Obtém todos os produtos",
            description = "Retorna uma lista de todos os produtos disponíveis.")
    @ApiResponse(responseCode = "200", description = "Lista de produtos obtida com sucesso",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Produto.class))))
    @ApiResponse(responseCode = "503", description = "Serviço indisponível (fallback)")
    public ResponseEntity getAll(@RequestHeader Map<String, String> headers){
        List<Produto> all = produtoService.getAll();
        LOGGER.info("GET ALL:" + all);
        LOGGER.info("All Headers:" + headers.toString());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("server-name", nickName);
        return ResponseEntity.ok().headers(httpHeaders).body(all);
    }

    public ResponseEntity<List<Produto>> fallbackGetAll(Map<String, String> headers, Throwable t) {
        LOGGER.error("Fallback for getAll - Error: {}", t.getMessage());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("server-name", nickName);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .headers(httpHeaders)
                .body(Collections.emptyList());
    }

    @GetMapping("/{id}")
    @CircuitBreaker(name = "produtoService", fallbackMethod = "fallbackGetById")
    @Retry(name = "produtoService")
    @Operation(summary = "Obtém um produto por ID",
            description = "Retorna detalhes de um produto específico com base no ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Produto.class)))
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    @ApiResponse(responseCode = "503", description = "Serviço indisponível (fallback)")
    public ResponseEntity<?> getById(@PathVariable long id){
        try {
            Produto produto = produtoService.getById(id);
            LOGGER.info("GET BY ID" + produto);
            return ResponseEntity.ok(produto);
        }catch (ProdutoNotFoundException exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ResponsePayload.builder().message("Not Found").dateTime(LocalDateTime.now()).build()
            );
        }

    }

    public ResponseEntity<?> fallbackGetById(long id, Throwable t) {
        LOGGER.error("Fallback for getById - Error: {}", t.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Fallback response for getById: " + t.getMessage());
    }


    @DeleteMapping("/{id}")
    @CircuitBreaker(name = "produtoService", fallbackMethod = "fallbackDelete")
    @Operation(summary = "Deleta um produto",
            description = "Deleta um produto específico com base no ID fornecido. Retorna uma mensagem de confirmação em caso de sucesso.")
    @ApiResponse(responseCode = "200", description = "Produto deletado com sucesso",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(name = "Exemplo de resposta de sucesso", value = "{\"message\": \"Produto deleted successfully\"}")))
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    @ApiResponse(responseCode = "503", description = "Serviço indisponível (fallback)")
    public ResponseEntity<?> delete(@PathVariable long id){
        try {
            produtoService.deleteById(id);
            LOGGER.info("DELETE: {}", id);
            return ResponseEntity.ok().body("Produto deleted successfully");
        } catch (Exception e) {
            LOGGER.error("Error deleting produto with id {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting produto: " + e.getMessage());
        }
    }

    public ResponseEntity<?> fallbackDelete(long id, Throwable t) {
        LOGGER.error("Fallback for delete - Error: {}", t.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Fallback response for delete: " + t.getMessage());
    }

    @PostMapping
    @CircuitBreaker(name = "produtoService", fallbackMethod = "fallbackCreate")
    @Retry(name = "produtoService")
    @Operation(summary = "Cria um novo produto",
            description = "Cria um novo produto com as informações fornecidas no corpo da requisição.")
    @ApiResponse(responseCode = "201", description = "Produto criado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Produto.class)))
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    public ResponseEntity<?> create(@RequestBody Produto produto){
        try {
            Produto created = produtoService.create(produto);
            LOGGER.info("CREATE: {}", created);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            LOGGER.error("Error creating produto: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating produto: " + e.getMessage());
        }
    }
    public ResponseEntity<?> fallbackCreate(Produto produto, Throwable t) {
        LOGGER.error("Fallback for create - Error: {}", t.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Fallback response for create: " + t.getMessage());
    }

    @PutMapping("/{id}")
    @CircuitBreaker(name = "produtoService", fallbackMethod = "fallbackUpdate")
    @Operation(summary = "Atualiza um produto",
            description = "Atualiza as informações de um produto existente com base no ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Produto.class)))
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody Produto produto){
        try {
            Produto updated = produtoService.update(id, produto);
            LOGGER.info("UPDATE: " + updated);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            LOGGER.error("Error updating produto with id {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating produto: " + e.getMessage());
        }
    }

    public ResponseEntity<?> fallbackUpdate(long id, Produto produto, Throwable t) {
        LOGGER.error("Fallback for update - Error: {}", t.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Fallback response for update: " + t.getMessage());
    }
}
