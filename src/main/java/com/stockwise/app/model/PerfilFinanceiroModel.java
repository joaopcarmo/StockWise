package com.stockwise.app.model;

import com.stockwise.app.dto.PerfilFinanceiroDto;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "perfil_financeiro")
public class PerfilFinanceiroModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal rendaMensal;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal despesasMensais;

    @Column(nullable = false, length = 500)
    private String objetivoInvestimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UserModel usuario;

    public PerfilFinanceiroModel() {}

    public PerfilFinanceiroModel(PerfilFinanceiroDto dto) {
        this.id = dto.getId();
        this.rendaMensal = dto.getRendaMensal();
        this.despesasMensais = dto.getDespesasMensais();
        this.objetivoInvestimento = dto.getObjetivoInvestimento();
    }

    public PerfilFinanceiroDto toDto() {
        return new PerfilFinanceiroDto(this);
    }

    public BigDecimal getValorDisponivel() {
        return rendaMensal.subtract(despesasMensais);
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

    public UserModel getUsuario() { return usuario; }
    public void setUsuario(UserModel usuario) { this.usuario = usuario; }
}
