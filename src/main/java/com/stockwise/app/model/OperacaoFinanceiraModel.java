package com.stockwise.app.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "operacao_financeira")
public class OperacaoFinanceiraModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoOperacao;

    private Double valor;

    @Temporal(TemporalType.DATE)
    private Date data;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UserModel usuario;

    @ManyToOne
    @JoinColumn(name = "ativo_id")
    private AtivoFinanceiroModel ativo;

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

    public UserModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UserModel usuario) {
        this.usuario = usuario;
    }

    public AtivoFinanceiroModel getAtivo() {
        return ativo;
    }

    public void setAtivo(AtivoFinanceiroModel ativo) {
        this.ativo = ativo;
    }
}
