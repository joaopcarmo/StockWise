package com.stockwise.app.repositories;

import com.stockwise.app.model.AtivoFinanceiroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface AtivoFinanceiroRepository extends JpaRepository<AtivoFinanceiroModel, Long> {

    // Buscar ativos por usuário
    List<AtivoFinanceiroModel> findByUsuarioId(UUID usuarioId);

    // Buscar ativos por tipo de operação
    List<AtivoFinanceiroModel> findByTipoOperacao(String tipoOperacao);

    // Buscar ativos por usuário e tipo de operação
    List<AtivoFinanceiroModel> findByUsuarioIdAndTipoOperacao(UUID usuarioId, String tipoOperacao);

    // Buscar ativos por período de data
    List<AtivoFinanceiroModel> findByDataBetween(Date dataInicio, Date dataFim);

    // Buscar ativos por usuário e período
    List<AtivoFinanceiroModel> findByUsuarioIdAndDataBetween(UUID usuarioId, Date dataInicio, Date dataFim);

    // Query customizada para buscar ativos com valor mínimo
    @Query("SELECT a FROM AtivoFinanceiroModel a WHERE a.valor >= :valorMinimo")
    List<AtivoFinanceiroModel> findByValorMinimo(@Param("valorMinimo") Double valorMinimo);

    // Query customizada para somar valores por usuário
    @Query("SELECT SUM(a.valor) FROM AtivoFinanceiroModel a WHERE a.usuario.id = :usuarioId")
    Double calcularValorTotalPorUsuario(@Param("usuarioId") UUID usuarioId);

    // Query customizada para buscar ativos com detalhes do usuário
    @Query("SELECT a FROM AtivoFinanceiroModel a " +
            "JOIN FETCH a.usuario u " +
            "WHERE a.usuario.id = :usuarioId " +
            "ORDER BY a.data DESC")
    List<AtivoFinanceiroModel> findAtivosComDetalhesUsuario(@Param("usuarioId") UUID usuarioId);
}