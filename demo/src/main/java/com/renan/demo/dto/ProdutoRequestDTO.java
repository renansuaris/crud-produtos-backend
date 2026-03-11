package com.renan.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProdutoRequestDTO(
        @NotBlank(message = "O nome do produto é obrigatório.")
        String nome,

        @NotNull(message = "O preço é obrigatório.")
        @Positive(message = "O preço deve ser maior que zero.")
        BigDecimal preco,

        Long categoriaId
) {}
