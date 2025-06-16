package com.stockwise.app.model;

import com.stockwise.app.dto.MetaFinanceiraDto;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "meta_financeira")
public class MetaFinanceiraModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorObjetivo;

    @Column(nullable = false)
    private LocalDate prazo;

    @Column(nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UserModel usuario;

    public MetaFinanceiraModel() {}

    public MetaFinanceiraModel(MetaFinanceiraDto dto) {
        this.id = dto.getId();
        this.descricao = dto.getDescricao();
        this.valorObjetivo = dto.getValorObjetivo();
        this.prazo = dto.getPrazo();
        this.status = dto.getStatus();
    }

    public MetaFinanceiraDto toDto() {
        return new MetaFinanceiraDto(this);
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

    public UserModel getUsuario() { return usuario; }
    public void setUsuario(UserModel usuario) { this.usuario = usuario; }
}