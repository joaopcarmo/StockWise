package com.stockwise.app.repositories;

import com.stockwise.app.model.OperacaoFinanceiraModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OperacaoFinanceiraRepository extends JpaRepository<OperacaoFinanceiraModel, Long> {
    List<OperacaoFinanceiraModel> findByUsuarioId(UUID usuarioId);
}
