package com.stockwise.app.repositories;

import com.stockwise.app.model.HumorFinanceiroModel;
import com.stockwise.app.model.HumorFinanceiroModel.EstadoHumor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface HumorFinanceiroRepository extends JpaRepository<HumorFinanceiroModel, UUID> {

    List<HumorFinanceiroModel> findByUsuarioIdOrderByDataDesc(UUID usuarioId);

    List<HumorFinanceiroModel> findByUsuarioIdAndDataBetweenOrderByDataDesc(
            UUID usuarioId,
            LocalDateTime dataInicio,
            LocalDateTime dataFim
    );

    Optional<HumorFinanceiroModel> findTopByUsuarioIdOrderByDataDesc(UUID usuarioId);

    List<HumorFinanceiroModel> findByEstado(EstadoHumor estado);

    @Query("SELECT h FROM HumorFinanceiroModel h WHERE h.usuarioId = :usuarioId AND h.data BETWEEN :start AND :end")
    Optional<HumorFinanceiroModel> findByUsuarioIdAndDataHoje(
            @Param("usuarioId") UUID usuarioId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );


    @Query("SELECT COUNT(h) FROM HumorFinanceiroModel h WHERE h.usuarioId = :usuarioId AND h.estado = :estado")
    Long countByUsuarioIdAndEstado(@Param("usuarioId") UUID usuarioId, @Param("estado") EstadoHumor estado);

    @Query("SELECT h.estado, COUNT(h) FROM HumorFinanceiroModel h WHERE h.usuarioId = :usuarioId GROUP BY h.estado")
    List<Object[]> countEstadosByUsuarioId(@Param("usuarioId") UUID usuarioId);

    @Query("SELECT h FROM HumorFinanceiroModel h WHERE h.usuarioId = :usuarioId AND h.data >= :dataInicio ORDER BY h.data DESC")
    List<HumorFinanceiroModel> findByUsuarioIdAndDataAfter(@Param("usuarioId") UUID usuarioId, @Param("dataInicio") LocalDateTime dataInicio);

    boolean existsByUsuarioId(UUID usuarioId);

    void deleteByUsuarioId(UUID usuarioId);
}