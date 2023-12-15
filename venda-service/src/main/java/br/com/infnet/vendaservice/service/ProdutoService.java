package br.com.infnet.vendaservice.service;

import br.com.infnet.vendaservice.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {
    @Autowired
    ProdutoApiService produtoApiService;
    public List<Produto> getAllProdutos(List<Long> ids){
        return ids.stream().map(id -> produtoApiService.getById(id)).toList();

    }

}
