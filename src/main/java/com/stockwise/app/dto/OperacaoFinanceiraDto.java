package com.stockwise.app.dto;

import java.util.Date;
import java.util.UUID;

public class OperacaoFinanceiraDto {

    private Long id;
    private String tipoOperacao;
    private Double valor;
    private Date data;

    private UUID usuarioId;  // UUID compatível com UserModel
    private Long ativoId;    // Long compatível com AtivoFinanceiroModel

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoOperacao() {
        return tipoOperacao;
    }

    public void setTipoOperacao(String tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getAtivoId() {
        return ativoId;
    }

    public void setAtivoId(Long ativoId) {
        this.ativoId = ativoId;
    }
}
