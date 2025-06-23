package com.stockwise.app.services;

import com.stockwise.app.dto.FavoritoDto;
import com.stockwise.app.model.AtivoFinanceiroModel;
import com.stockwise.app.model.FavoritoModel;
import com.stockwise.app.model.UserModel;
import com.stockwise.app.repositories.AtivoFinanceiroRepository;
import com.stockwise.app.repositories.FavoritoRepository;
import com.stockwise.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FavoritoService {

    @Autowired
    private FavoritoRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AtivoFinanceiroRepository ativoRepository;

    // CREATE - Salvar favorito
    public FavoritoModel salvar(FavoritoDto dto) {
        UserModel usuario = userRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        AtivoFinanceiroModel ativo = ativoRepository.findById(dto.getAtivoId())
                .orElseThrow(() -> new RuntimeException("Ativo não encontrado"));

        FavoritoModel favorito = new FavoritoModel();
        favorito.setUsuario(usuario);
        favorito.setAtivo(ativo);

        return repository.save(favorito);
    }

    // READ - Listar todos os favoritos
    public List<FavoritoModel> listarTodos() {
        return repository.findAll();
    }

    // READ - Buscar favorito por ID
    public FavoritoModel buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Favorito não encontrado"));
    }

    // READ - Listar favoritos por usuário
    public List<FavoritoModel> listarPorUsuario(UUID usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }

    // UPDATE - Atualizar favorito
    public FavoritoModel atualizar(Long id, FavoritoDto dto) {
        FavoritoModel favorito = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Favorito não encontrado"));

        // Validar e atualizar usuário se fornecido
        if (dto.getUsuarioId() != null) {
            UserModel usuario = userRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            favorito.setUsuario(usuario);
        }

        // Validar e atualizar ativo se fornecido
        if (dto.getAtivoId() != null) {
            AtivoFinanceiroModel ativo = ativoRepository.findById(dto.getAtivoId())
                    .orElseThrow(() -> new RuntimeException("Ativo não encontrado"));
            favorito.setAtivo(ativo);
        }

        return repository.save(favorito);
    }

    // DELETE - Deletar favorito
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Favorito não encontrado");
        }
        repository.deleteById(id);
    }
}