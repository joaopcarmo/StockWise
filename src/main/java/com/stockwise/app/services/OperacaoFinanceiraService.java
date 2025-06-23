package com.stockwise.app.services;

import com.stockwise.app.dto.OperacaoFinanceiraDto;
import com.stockwise.app.model.AtivoFinanceiroModel;
import com.stockwise.app.model.OperacaoFinanceiraModel;
import com.stockwise.app.model.UserModel;
import com.stockwise.app.repositories.AtivoFinanceiroRepository;
import com.stockwise.app.repositories.OperacaoFinanceiraRepository;
import com.stockwise.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
public class OperacaoFinanceiraService {

    @Autowired
    private OperacaoFinanceiraRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AtivoFinanceiroRepository ativoRepository;

    public OperacaoFinanceiraModel salvar(OperacaoFinanceiraDto dto) {
        UserModel usuario = userRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        AtivoFinanceiroModel ativo = ativoRepository.findById(dto.getAtivoId())
                .orElseThrow(() -> new EntityNotFoundException("Ativo não encontrado"));

        OperacaoFinanceiraModel model = new OperacaoFinanceiraModel();
        model.setTipoOperacao(dto.getTipoOperacao());
        model.setValor(dto.getValor());
        model.setData(dto.getData());
        model.setUsuario(usuario);
        model.setAtivo(ativo);

        return repository.save(model);
    }

    public OperacaoFinanceiraModel atualizar(Long id, OperacaoFinanceiraDto dto) {
        // Busca a operação existente
        OperacaoFinanceiraModel operacaoExistente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Operação financeira não encontrada"));

        // Valida e busca o usuário se fornecido
        if (dto.getUsuarioId() != null) {
            UserModel usuario = userRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
            operacaoExistente.setUsuario(usuario);
        }

        // Valida e busca o ativo se fornecido
        if (dto.getAtivoId() != null) {
            AtivoFinanceiroModel ativo = ativoRepository.findById(dto.getAtivoId())
                    .orElseThrow(() -> new EntityNotFoundException("Ativo não encontrado"));
            operacaoExistente.setAtivo(ativo);
        }

        // Atualiza apenas os campos que foram fornecidos no DTO
        if (dto.getTipoOperacao() != null) {
            operacaoExistente.setTipoOperacao(dto.getTipoOperacao());
        }

        if (dto.getValor() != null) {
            operacaoExistente.setValor(dto.getValor());
        }

        if (dto.getData() != null) {
            operacaoExistente.setData(dto.getData());
        }

        return repository.save(operacaoExistente);
    }

    public List<OperacaoFinanceiraModel> listarPorUsuario(UUID usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }

    public OperacaoFinanceiraModel buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Operação não encontrada"));
    }

    public void deletar(Long id) {
        // Verifica se a operação existe antes de deletar
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Operação não encontrada");
        }
        repository.deleteById(id);
    }
}