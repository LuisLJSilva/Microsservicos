package br.com.infnet.vendaservice.service;

import br.com.infnet.vendaservice.model.Produto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "produto-service", url = "http://localhost:9091/api/produto")
public interface ProdutoApiService {
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    Produto getById(@PathVariable Long id);


}
