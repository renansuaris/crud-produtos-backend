package com.renan.demo.service;

import com.renan.demo.model.Categoria;
import com.renan.demo.repository.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Transactional
    public Categoria salvar(Categoria categoria) {
        if (categoriaRepository.existsByNome(categoria.getNome())) {
            throw new IllegalArgumentException("Já existe uma categoria cadastrada com o nome: " + categoria.getNome());
        }
        return categoriaRepository.save(categoria);
    }

    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com o ID: " + id));
    }

    @Transactional
    public Categoria atualizar(Long id, Categoria categoriaAtualizada) {
        Categoria categoriaExistente = buscarPorId(id);

        if (!categoriaExistente.getNome().equals(categoriaAtualizada.getNome()) &&
                categoriaRepository.existsByNome(categoriaAtualizada.getNome())) {
            throw new IllegalArgumentException("Já existe uma categoria cadastrada com este nome.");
        }

        categoriaExistente.setNome(categoriaAtualizada.getNome());
        return categoriaRepository.save(categoriaExistente);
    }

    @Transactional
    public void excluir(Long id) {
        Categoria categoria = buscarPorId(id);
        categoriaRepository.delete(categoria);
    }

}
