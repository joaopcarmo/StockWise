package com.stockwise.app.services;

import com.stockwise.app.dto.EmissorDto;
import com.stockwise.app.model.EmissorModel;
import com.stockwise.app.repositories.EmissorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmissorService {

    @Autowired
    private EmissorRepository repository;

    // CREATE - Salvar emissor
    public EmissorModel salvar(EmissorDto dto) {
        EmissorModel model = new EmissorModel();
        model.setNome(dto.getNome());
        model.setSetor(dto.getSetor());
        return repository.save(model);
    }

    // READ - Listar todos os emissores
    public List<EmissorModel> listarTodos() {
        return repository.findAll();
    }

    // READ - Buscar emissor por ID
    public EmissorModel buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Emissor não encontrado"));
    }

    // UPDATE - Atualizar emissor
    public EmissorModel atualizar(Long id, EmissorDto dto) {
        EmissorModel emissor = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Emissor não encontrado"));

        // Atualizar campos se fornecidos
        if (dto.getNome() != null && !dto.getNome().trim().isEmpty()) {
            emissor.setNome(dto.getNome());
        }

        if (dto.getSetor() != null) {
            emissor.setSetor(dto.getSetor());
        }

        return repository.save(emissor);
    }

    // DELETE - Deletar emissor
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Emissor não encontrado");
        }
        repository.deleteById(id);
    }
}