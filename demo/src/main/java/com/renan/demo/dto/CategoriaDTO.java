package com.renan.demo.dto;

import com.renan.demo.model.Categoria;
import jakarta.validation.constraints.NotBlank;

public record CategoriaDTO(
        Long id,
        @NotBlank(message = "O nome da categoria é obrigatório.")
        String nome
) {
    public CategoriaDTO(Categoria categoria) {
        this(categoria.getId(), categoria.getNome());
    }

    public Categoria toEntity() {
        Categoria categoria = new Categoria();
        categoria.setId(id);
        categoria.setNome(nome);
        return categoria;
    }
}
