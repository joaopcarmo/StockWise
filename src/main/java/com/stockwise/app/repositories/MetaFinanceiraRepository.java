package com.stockwise.app.repositories;

import com.stockwise.app.model.MetaFinanceiraModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MetaFinanceiraRepository extends JpaRepository<MetaFinanceiraModel, UUID> {
    List<MetaFinanceiraModel> findByUsuarioId(UUID usuarioId);
}