package com.stockwise.app.services;

import com.stockwise.app.dto.MetaFinanceiraDto;
import com.stockwise.app.model.MetaFinanceiraModel;
import com.stockwise.app.model.UserModel;
import com.stockwise.app.repositories.MetaFinanceiraRepository;
import com.stockwise.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class MetaFinanceiraService {

    @Autowired
    private MetaFinanceiraRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<MetaFinanceiraDto> findByUsuario(UUID usuarioId) {
        return repository.findByUsuarioId(usuarioId)
                .stream().map(MetaFinanceiraModel::toDto).toList();
    }

    @Transactional
    public MetaFinanceiraDto save(MetaFinanceiraDto dto) {
        UserModel usuario = userRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        MetaFinanceiraModel model = dto.toModel();
        model.setUsuario(usuario);
        return repository.save(model).toDto();
    }

    @Transactional
    public MetaFinanceiraDto update(UUID id, MetaFinanceiraDto dto) {
        MetaFinanceiraModel model = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Meta não encontrada"));
        model.setDescricao(dto.getDescricao());
        model.setValorObjetivo(dto.getValorObjetivo());
        model.setPrazo(dto.getPrazo());
        model.setStatus(dto.getStatus());
        return repository.save(model).toDto();
    }

    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Meta não encontrada");
        }
        repository.deleteById(id);
    }
}