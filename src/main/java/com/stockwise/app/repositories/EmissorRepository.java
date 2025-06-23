package com.stockwise.app.repositories;

import com.stockwise.app.model.EmissorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmissorRepository extends JpaRepository<EmissorModel, Long> {

    // Buscar emissor por nome (case insensitive)
    Optional<EmissorModel> findByNomeIgnoreCase(String nome);

    // Buscar emissores por setor
    List<EmissorModel> findBySetor(String setor);

    // Buscar emissores por setor (case insensitive)
    List<EmissorModel> findBySetorIgnoreCase(String setor);

    // Verificar se existe emissor com o nome
    boolean existsByNomeIgnoreCase(String nome);

    // Buscar emissores que contenham o nome (busca parcial)
    List<EmissorModel> findByNomeContainingIgnoreCase(String nome);

    // Query customizada para buscar emissores ordenados por nome
    @Query("SELECT e FROM EmissorModel e ORDER BY e.nome ASC")
    List<EmissorModel> findAllOrderByNome();

    // Query customizada para buscar emissores por setor ordenados por nome
    @Query("SELECT e FROM EmissorModel e WHERE e.setor = :setor ORDER BY e.nome ASC")
    List<EmissorModel> findBySetorOrderByNome(@Param("setor") String setor);

    // Query customizada para contar emissores por setor
    @Query("SELECT COUNT(e) FROM EmissorModel e WHERE e.setor = :setor")
    Long countBySetor(@Param("setor") String setor);

    // Query customizada para listar setores únicos
    @Query("SELECT DISTINCT e.setor FROM EmissorModel e WHERE e.setor IS NOT NULL ORDER BY e.setor")
    List<String> findDistinctSetores();
}