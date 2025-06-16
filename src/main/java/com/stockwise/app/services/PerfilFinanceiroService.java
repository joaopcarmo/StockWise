package com.stockwise.app.services;

import com.stockwise.app.dto.PerfilFinanceiroDto;
import com.stockwise.app.model.PerfilFinanceiroModel;
import com.stockwise.app.model.UserModel;
import com.stockwise.app.repositories.PerfilFinanceiroRepository;
import com.stockwise.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class PerfilFinanceiroService {

    private final PerfilFinanceiroRepository perfilFinanceiroRepository;
    private final UserRepository userRepository;

    @Autowired
    public PerfilFinanceiroService(PerfilFinanceiroRepository perfilFinanceiroRepository,
                                   UserRepository userRepository) {
        this.perfilFinanceiroRepository = perfilFinanceiroRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<PerfilFinanceiroDto> findAll() {
        return perfilFinanceiroRepository.findAll()
                .stream()
                .map(PerfilFinanceiroDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public PerfilFinanceiroDto findById(UUID id) {
        return perfilFinanceiroRepository.findById(id)
                .map(PerfilFinanceiroDto::new)
                .orElseThrow(() -> new IllegalArgumentException("Perfil financeiro não encontrado"));
    }

    @Transactional(readOnly = true)
    public PerfilFinanceiroDto findByUsuarioId(UUID usuarioId) {
        return perfilFinanceiroRepository.findByUsuarioId(usuarioId)
                .map(PerfilFinanceiroDto::new)
                .orElseThrow(() -> new IllegalArgumentException("Perfil financeiro não encontrado para este usuário"));
    }

    @Transactional
    public PerfilFinanceiroDto save(PerfilFinanceiroDto perfilDto) {
        UserModel usuario = userRepository.findById(perfilDto.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        if (perfilFinanceiroRepository.existsByUsuarioId(perfilDto.getUsuarioId())) {
            throw new IllegalArgumentException("Usuário já possui um perfil financeiro");
        }

        validarPerfil(perfilDto);

        PerfilFinanceiroModel perfilModel = perfilDto.toModel();
        perfilModel.setUsuario(usuario);

        return perfilFinanceiroRepository.save(perfilModel).toDto();
    }

    @Transactional
    public PerfilFinanceiroDto update(UUID id, PerfilFinanceiroDto perfilDtoAtualizado) {
        PerfilFinanceiroModel perfilExistente = perfilFinanceiroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Perfil financeiro não encontrado"));

        validarPerfil(perfilDtoAtualizado);

        perfilExistente.setRendaMensal(perfilDtoAtualizado.getRendaMensal());
        perfilExistente.setDespesasMensais(perfilDtoAtualizado.getDespesasMensais());
        perfilExistente.setObjetivoInvestimento(perfilDtoAtualizado.getObjetivoInvestimento());

        return perfilFinanceiroRepository.save(perfilExistente).toDto();
    }

    @Transactional
    public PerfilFinanceiroDto updateByUsuarioId(UUID usuarioId, PerfilFinanceiroDto perfilDtoAtualizado) {
        PerfilFinanceiroModel perfilExistente = perfilFinanceiroRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Perfil financeiro não encontrado para este usuário"));

        perfilDtoAtualizado.setId(perfilExistente.getId());

        return update(perfilExistente.getId(), perfilDtoAtualizado);
    }

    @Transactional
    public void delete(UUID id) {
        if (!perfilFinanceiroRepository.existsById(id)) {
            throw new IllegalArgumentException("Perfil financeiro não encontrado");
        }
        perfilFinanceiroRepository.deleteById(id);
    }

    @Transactional
    public void deleteByUsuarioId(UUID usuarioId) {
        if (!perfilFinanceiroRepository.existsByUsuarioId(usuarioId)) {
            throw new IllegalArgumentException("Perfil financeiro não encontrado para este usuário");
        }
        perfilFinanceiroRepository.deleteByUsuarioId(usuarioId);
    }

    @Transactional(readOnly = true)
    public boolean existsByUsuarioId(UUID usuarioId) {
        return perfilFinanceiroRepository.existsByUsuarioId(usuarioId);
    }

    private void validarPerfil(PerfilFinanceiroDto dto) {
        if (dto.getDespesasMensais().compareTo(dto.getRendaMensal()) > 0) {
            throw new IllegalArgumentException("As despesas mensais não podem ser maiores que a renda mensal");
        }
    }
}