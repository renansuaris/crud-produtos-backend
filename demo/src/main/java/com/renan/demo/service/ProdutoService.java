package com.renan.demo.service;

import com.renan.demo.model.Categoria;
import com.renan.demo.model.Produto;
import com.renan.demo.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaService categoriaService;

    @Transactional
    public Produto salvar(Produto produto) {
        Categoria categoria = categoriaService.buscarPorId(produto.getCategoria().getId());
        produto.setCategoria(categoria);

        return produtoRepository.save(produto);
    }

    public Page<Produto> listarTodos(Pageable pageable) {
        return produtoRepository.findAll(pageable);
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o ID: " + id));
    }

    @Transactional
    public Produto atualizar(Long id, Produto produtoAtualizado) {
        Produto produtoExistente = buscarPorId(id);

        Categoria categoria = categoriaService.buscarPorId(produtoAtualizado.getCategoria().getId());

        produtoExistente.setNome(produtoAtualizado.getNome());
        produtoExistente.setPreco(produtoAtualizado.getPreco());
        produtoExistente.setCategoria(categoria);

        return produtoRepository.save(produtoExistente);
    }

    @Transactional
    public void excluir(Long id) {
        Produto produto = buscarPorId(id);
        produtoRepository.delete(produto);
    }
}
