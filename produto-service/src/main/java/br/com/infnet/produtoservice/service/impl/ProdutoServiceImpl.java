package br.com.infnet.produtoservice.service.impl;

import br.com.infnet.produtoservice.exception.ProdutoNotFoundException;
import br.com.infnet.produtoservice.model.Produto;
import br.com.infnet.produtoservice.repository.ProdutoRepository;
import br.com.infnet.produtoservice.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoServiceImpl implements ProdutoService {
    @Autowired
    ProdutoRepository repository;

    @Override
    public List<Produto> getAll() {
        return repository.findAll();
    }

    @Override
    public Produto create(Produto produto) {
        return repository.save(produto);
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }


    @Override
    public Produto update(long id, Produto produto) {
        produto.setId(id);
        return create(produto);
    }

    @Override
    public Produto getById(long id) {
        return repository.findById(id).orElseThrow(ProdutoNotFoundException::new);
    }
}
