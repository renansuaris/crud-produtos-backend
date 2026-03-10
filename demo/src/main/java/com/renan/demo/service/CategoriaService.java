package com.renan.demo.service;

import com.renan.demo.dto.CategoriaDTO;
import com.renan.demo.model.Categoria;
import com.renan.demo.repository.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Transactional
    public CategoriaDTO salvar(CategoriaDTO dto) {
        if (categoriaRepository.existsByNome(dto.nome())) {
            throw new IllegalArgumentException("Já existe uma categoria cadastrada com o nome: " + dto.nome());
        }
        Categoria categoria = new Categoria();
        categoria.setNome(dto.nome());
        return new CategoriaDTO(categoriaRepository.save(categoria));
    }

    public List<CategoriaDTO> listarTodas() {
        return categoriaRepository.findAll().stream()
                .map(CategoriaDTO::new)
                .collect(Collectors.toList());
    }

    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com o ID: " + id));
    }

    public CategoriaDTO buscarDTO(Long id) {
        return new CategoriaDTO(buscarPorId(id));
    }

    @Transactional
    public CategoriaDTO atualizar(Long id, CategoriaDTO dto) {
        Categoria categoriaExistente = buscarPorId(id);

        if (!categoriaExistente.getNome().equals(dto.nome()) &&
                categoriaRepository.existsByNome(dto.nome())) {
            throw new IllegalArgumentException("Já existe uma categoria cadastrada com este nome.");
        }

        categoriaExistente.setNome(dto.nome());
        return new CategoriaDTO(categoriaRepository.save(categoriaExistente));
    }

    @Transactional
    public void excluir(Long id) {
        Categoria categoria = buscarPorId(id);
        categoriaRepository.delete(categoria);
    }

}
