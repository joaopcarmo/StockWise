package com.stockwise.app.services;

import com.stockwise.app.dto.AtivoFinanceiroDto;
import com.stockwise.app.model.AtivoFinanceiroModel;
import com.stockwise.app.model.UserModel;
import com.stockwise.app.repositories.AtivoFinanceiroRepository;
import com.stockwise.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AtivoFinanceiroService {

    @Autowired
    private AtivoFinanceiroRepository repository;

    @Autowired
    private UserRepository userRepository;

    // CREATE - Salvar ativo financeiro
    public AtivoFinanceiroModel salvar(AtivoFinanceiroDto dto) {
        UserModel usuario = userRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        AtivoFinanceiroModel model = new AtivoFinanceiroModel();
        model.setTipoOperacao(dto.getTipoOperacao());
        model.setValor(dto.getValor());
        model.setData(dto.getData());
        model.setUsuario(usuario);

        return repository.save(model);
    }

    // READ - Listar todos os ativos financeiros
    public List<AtivoFinanceiroModel> listarTodos() {
        return repository.findAll();
    }

    // READ - Buscar ativo financeiro por ID
    public AtivoFinanceiroModel buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ativo financeiro não encontrado"));
    }

    // READ - Listar ativos financeiros por usuário
    public List<AtivoFinanceiroModel> listarPorUsuario(UUID usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }

    // UPDATE - Atualizar ativo financeiro
    public AtivoFinanceiroModel atualizar(Long id, AtivoFinanceiroDto dto) {
        AtivoFinanceiroModel ativo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ativo financeiro não encontrado"));

        // Atualizar campos se fornecidos
        if (dto.getTipoOperacao() != null) {
            ativo.setTipoOperacao(dto.getTipoOperacao());
        }

        if (dto.getValor() != null) {
            ativo.setValor(dto.getValor());
        }

        if (dto.getData() != null) {
            ativo.setData(dto.getData());
        }

        // Atualizar usuário se fornecido
        if (dto.getUsuarioId() != null) {
            UserModel usuario = userRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            ativo.setUsuario(usuario);
        }

        return repository.save(ativo);
    }

    // DELETE - Deletar ativo financeiro
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Ativo financeiro não encontrado");
        }
        repository.deleteById(id);
    }
}