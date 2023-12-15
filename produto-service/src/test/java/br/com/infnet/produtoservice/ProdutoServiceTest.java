package br.com.infnet.produtoservice;

import br.com.infnet.produtoservice.exception.ProdutoNotFoundException;
import br.com.infnet.produtoservice.model.Produto;
import br.com.infnet.produtoservice.repository.ProdutoRepository;
import br.com.infnet.produtoservice.service.ProdutoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles(value = "test")
public class ProdutoServiceTest {
    Logger LOGGER = LoggerFactory.getLogger(ProdutoServiceTest.class);
    @Autowired
    ProdutoRepository repository;
    @Autowired
    ProdutoService produtoService;

    @BeforeEach
    public void setUp(){
        Produto rashguard = Produto.builder().id(1).nome("Rashguard Keiko").valor(new BigDecimal("169.99")).build();
        Produto kimono = Produto.builder().id(2).nome("Kimono Koral A1").valor(new BigDecimal("499.99")).build();
        List<Produto> produtos = List.of(rashguard,kimono);
        repository.saveAll(produtos);
        LOGGER.info("Before Each");
    }

    @Test
    public void testaGetAll(){
        List<Produto> all = produtoService.getAll();
        Produto produto = all.get(0);
        assertEquals(2, all.size());
        assertEquals("Rashguard Keiko",produto.getNome());
        LOGGER.info("Teste GET ALL");
    }


    @Test
    public void testaGetById(){
        Produto rashguard = produtoService.getById(1);
        assertEquals("Rashguard Keiko", rashguard.getNome());
        assertEquals(new BigDecimal("169.99"), rashguard.getValor());
        assertThrows(ProdutoNotFoundException.class, () ->{
            Produto surprise = produtoService.getById(39);
        });


    }


    @Test
    public void testaCreate(){
        Produto mochila = Produto.builder().nome("Mochila Casca Grossa").valor(new BigDecimal("249.99")).build();
        Produto created = produtoService.create(mochila);
        int size = produtoService.getAll().size();
        assertEquals(3,size);
        assertNotEquals(0, created.getId());
    }

    @Test
    public void testaDelete(){
        produtoService.deleteById(1);
        int size = produtoService.getAll().size();
        assertEquals(2,size);
//        assertEquals(1,size);
    }

    @Test
    public void testaUpdate(){
        Produto kimono = Produto.builder().id(2).nome("Kimono Koral").valor(new BigDecimal("329.99")).build();
        produtoService.update(2,kimono);
        int size = produtoService.getAll().size();
        assertEquals(3,size);
//        Produto kimonoFound = produtoService.getById(1);
//        assertEquals("Kimono Koral", kimonoFound.getNome());
//        assertEquals(new BigDecimal("329.99"), kimonoFound.getValor());
    }

    @AfterEach
    public void tearDown(){
        repository.deleteAll();
    }
}
