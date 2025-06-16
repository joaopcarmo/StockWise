package com.stockwise.app.dto;

import com.stockwise.app.model.PerfilFinanceiroModel;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public class PerfilFinanceiroDto {

    @Null(message = "O ID deve ser nulo na criação")
    private UUID id;

    @NotNull(message = "A renda mensal é obrigatória")
    @DecimalMin(value = "0.01", message = "A renda mensal deve ser maior que zero")
    @Digits(integer = 8, fraction = 2, message = "Formato inválido para renda mensal")
    private BigDecimal rendaMensal;

    @NotNull(message = "As despesas mensais são obrigatórias")
    @DecimalMin(value = "0.00", message = "As despesas mensais devem ser maior ou igual a zero")
    @Digits(integer = 8, fraction = 2, message = "Formato inválido para despesas mensais")
    private BigDecimal despesasMensais;

    @NotBlank(message = "O objetivo de investimento é obrigatório")
    @Size(max = 500, message = "O objetivo de investimento não pode exceder 500 caracteres")
    private String objetivoInvestimento;

    @NotNull(message = "O ID do usuário é obrigatório")
    private UUID usuarioId;

    public PerfilFinanceiroDto() {}

    public PerfilFinanceiroDto(BigDecimal rendaMensal, BigDecimal despesasMensais,
                               String objetivoInvestimento, UUID usuarioId) {
        this.rendaMensal = rendaMensal;
        this.despesasMensais = despesasMensais;
        this.objetivoInvestimento = objetivoInvestimento;
        this.usuarioId = usuarioId;
    }

    public PerfilFinanceiroDto(PerfilFinanceiroModel model) {
        this.id = model.getId();
        this.rendaMensal = model.getRendaMensal();
        this.despesasMensais = model.getDespesasMensais();
        this.objetivoInvestimento = model.getObjetivoInvestimento();
        this.usuarioId = model.getUsuario().getId();
    }

    public PerfilFinanceiroModel toModel() {
        return new PerfilFinanceiroModel(this);
    }

    // Getters e Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public BigDecimal getRendaMensal() { return rendaMensal; }
    public void setRendaMensal(BigDecimal rendaMensal) { this.rendaMensal = rendaMensal; }

    public BigDecimal getDespesasMensais() { return despesasMensais; }
    public void setDespesasMensais(BigDecimal despesasMensais) { this.despesasMensais = despesasMensais; }

    public String getObjetivoInvestimento() { return objetivoInvestimento; }
    public void setObjetivoInvestimento(String objetivoInvestimento) { this.objetivoInvestimento = objetivoInvestimento; }

    public UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }
}
