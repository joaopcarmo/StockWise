package com.stockwise.app.repositories;

import com.stockwise.app.model.PerfilFinanceiroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PerfilFinanceiroRepository extends JpaRepository<PerfilFinanceiroModel, UUID> {

    Optional<PerfilFinanceiroModel> findByUsuarioId(UUID usuarioId);

    boolean existsByUsuarioId(UUID usuarioId);

    void deleteByUsuarioId(UUID usuarioId);

    @Query("SELECT p FROM PerfilFinanceiroModel p WHERE p.rendaMensal BETWEEN :rendaMin AND :rendaMax")
    List<PerfilFinanceiroModel> findByRendaMensalBetween(BigDecimal rendaMin, BigDecimal rendaMax);

    @Query("SELECT p FROM PerfilFinanceiroModel p WHERE (p.rendaMensal - p.despesasMensais) > :valorMinimo")
    List<PerfilFinanceiroModel> findByValorDisponivelMaiorQue(BigDecimal valorMinimo);

    @Query("SELECT p FROM PerfilFinanceiroModel p WHERE LOWER(p.objetivoInvestimento) LIKE LOWER(CONCAT('%', :objetivo, '%'))")
    List<PerfilFinanceiroModel> findByObjetivoInvestimentoContainingIgnoreCase(String objetivo);

    @Query("SELECT p FROM PerfilFinanceiroModel p ORDER BY (p.rendaMensal - p.despesasMensais) DESC")
    List<PerfilFinanceiroModel> findAllOrderByValorDisponivelDesc();
}
