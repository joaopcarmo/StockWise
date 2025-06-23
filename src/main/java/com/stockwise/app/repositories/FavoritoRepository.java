package com.stockwise.app.repositories;

import com.stockwise.app.model.FavoritoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FavoritoRepository extends JpaRepository<FavoritoModel, Long> {

    // Buscar favoritos por usuário
    List<FavoritoModel> findByUsuarioId(UUID usuarioId);

    // Buscar favoritos por ativo
    List<FavoritoModel> findByAtivoId(Long ativoId);

    // Verificar se já existe favorito para um usuário e ativo específico
    boolean existsByUsuarioIdAndAtivoId(UUID usuarioId, Long ativoId);

    // Buscar favorito específico por usuário e ativo
    Optional<FavoritoModel> findByUsuarioIdAndAtivoId(UUID usuarioId, Long ativoId);

    // Query customizada para buscar favoritos com informações completas
    @Query("SELECT f FROM FavoritoModel f " +
            "JOIN FETCH f.usuario u " +
            "JOIN FETCH f.ativo a " +
            "WHERE f.usuario.id = :usuarioId")
    List<FavoritoModel> findFavoritosComDetalhes(@Param("usuarioId") UUID usuarioId);
}