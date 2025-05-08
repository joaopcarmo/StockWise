package com.stockwise.app.dto;

import com.stockwise.app.model.UserModel;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

import org.springframework.beans.BeanUtils;

public class UserDto {
    /*
     * Quando um fornecedor é criado, o ID é gerado automaticamente.
     * Quando um fornecedor é lido, atualizado ou excluído, o ID é
     * enviado na requisição para identificar o fornecedor.
     */
    private UUID id;

    @NotNull
    @NotBlank
    private String nome;

    @NotNull
    @NotBlank
    private String telefone;
    @NotNull
    @NotBlank

    private String endereco;

    public UserDto() {
    }

    public UserDto(UUID id, String nome, String telefone, String endereco) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public UserDto(UserModel userModel) throws IllegalArgumentException {
        if (userModel == null) {
            throw new IllegalArgumentException("UsuarioModel não pode ser null");
        }
        BeanUtils.copyProperties(userModel, this);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public UserModel toModel() {
        if (id == null){
            return new UserModel(getNome(), getTelefone(), getEndereco());
        }
        return new UserModel(this);
    }
}
