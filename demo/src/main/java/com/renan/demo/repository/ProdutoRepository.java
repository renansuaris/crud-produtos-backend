package com.renan.demo.repository;

import com.renan.demo.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Modifying
    @Query("UPDATE Produto p SET p.categoria = null WHERE p.categoria.id = :categoriaId")
    void desvincularProdutos(Long categoriaId);

    boolean existsByNome(String nome);

}
