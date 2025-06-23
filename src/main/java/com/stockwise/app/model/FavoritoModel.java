package com.stockwise.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "favorito")
public class FavoritoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
