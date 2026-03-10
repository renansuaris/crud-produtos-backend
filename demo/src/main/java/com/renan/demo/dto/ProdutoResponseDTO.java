package com.renan.demo.dto;

import com.renan.demo.model.Produto;
import java.math.BigDecimal;

public record ProdutoResponseDTO(
        Long id,
        String nome,
        BigDecimal preco,
        CategoriaDTO categoria
) {
    public ProdutoResponseDTO(Produto produto) {
        this(produto.getId(),
             produto.getNome(),
             produto.getPreco(),
             new CategoriaDTO(produto.getCategoria()));
    }
}
