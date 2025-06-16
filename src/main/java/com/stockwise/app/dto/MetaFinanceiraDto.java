package com.stockwise.app.dto;

import com.stockwise.app.model.MetaFinanceiraModel;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class MetaFinanceiraDto {

    private UUID id;

    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;

    @NotNull(message = "Valor objetivo é obrigatório")
    @DecimalMin(value = "0.01", message = "O valor deve ser positivo")
    private BigDecimal valorObjetivo;

    @NotNull(message = "Prazo é obrigatório")
    private LocalDate prazo;

    @NotBlank(message = "Status é obrigatório")
    private String status;

    @NotNull(message = "ID do usuário é obrigatório")
    private UUID usuarioId;

    public MetaFinanceiraDto() {}

    public MetaFinanceiraDto(MetaFinanceiraModel model) {
        this.id = model.getId();
        this.descricao = model.getDescricao();
        this.valorObjetivo = model.getValorObjetivo();
        this.prazo = model.getPrazo();
        this.status = model.getStatus();
        this.usuarioId = model.getUsuario().getId();
    }

    public MetaFinanceiraModel toModel() {
        return new MetaFinanceiraModel(this);
    }

    // Getters e setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getValorObjetivo() { return valorObjetivo; }
    public void setValorObjetivo(BigDecimal valorObjetivo) { this.valorObjetivo = valorObjetivo; }

    public LocalDate getPrazo() { return prazo; }
    public void setPrazo(LocalDate prazo) { this.prazo = prazo; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }
}