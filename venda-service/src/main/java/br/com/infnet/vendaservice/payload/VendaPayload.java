package br.com.infnet.vendaservice.payload;

import lombok.Data;

import java.util.List;

@Data
public class VendaPayload {
    private List<Long> produtos;
}
