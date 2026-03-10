package com.renan.demo.service;

import com.renan.demo.dto.ProdutoRequestDTO;
import com.renan.demo.dto.ProdutoResponseDTO;
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
    public ProdutoResponseDTO salvar(ProdutoRequestDTO dto) {
        Categoria categoria = categoriaService.buscarPorId(dto.categoriaId());
        
        Produto produto = new Produto();
        produto.setNome(dto.nome());
        produto.setPreco(dto.preco());
        produto.setCategoria(categoria);

        return new ProdutoResponseDTO(produtoRepository.save(produto));
    }

    public Page<ProdutoResponseDTO> listarTodos(Pageable pageable) {
        return produtoRepository.findAll(pageable).map(ProdutoResponseDTO::new);
    }

    public ProdutoResponseDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o ID: " + id));
        return new ProdutoResponseDTO(produto);
    }

    @Transactional
    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o ID: " + id));

        Categoria categoria = categoriaService.buscarPorId(dto.categoriaId());

        produtoExistente.setNome(dto.nome());
        produtoExistente.setPreco(dto.preco());
        produtoExistente.setCategoria(categoria);

        return new ProdutoResponseDTO(produtoRepository.save(produtoExistente));
    }

    @Transactional
    public void excluir(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new EntityNotFoundException("Produto não encontrado com o ID: " + id);
        }
        produtoRepository.deleteById(id);
    }
}
