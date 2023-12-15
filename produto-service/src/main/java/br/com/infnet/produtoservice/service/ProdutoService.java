package br.com.infnet.produtoservice.service;

import br.com.infnet.produtoservice.model.Produto;

import java.util.List;

public interface ProdutoService {
    List<Produto> getAll();

    Produto create(Produto produto);

    void deleteById(long id);

    Produto update(long id, Produto produto);

    Produto getById(long id);
}
