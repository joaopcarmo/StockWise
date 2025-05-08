package com.stockwise.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stockwise.app.model.UserModel;
import jakarta.validation.constraints.*;

import java.util.UUID;

import org.springframework.beans.BeanUtils;

public class UserDto {
    /*
     * Quando um fornecedor é criado, o ID é gerado automaticamente.
     * Quando um fornecedor é lido, atualizado ou excluído, o ID é
     * enviado na requisição para identificar o fornecedor.
     */
    @Null(message = "O ID deve ser nulo na criação")
    private UUID id;

    @NotNull
    @NotBlank
    private String nome;

    @NotNull
    @NotBlank
    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve conter 10 ou 11 dígitos")

    private String telefone;
    @NotNull
    @NotBlank

    private String endereco;

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    @Pattern(regexp = ".*\\d.*", message = "A senha deve conter pelo menos um número")
    @JsonIgnore
    private String senha;

    private boolean admin = false;

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public UserDto() {
    }

    public UserDto(UUID id, String nome, String telefone, String endereco, String email, String senha, boolean admin) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.email = email;
        this.senha = senha;
        this.admin = admin;
    }

    public UserDto(UserModel userModel) throws IllegalArgumentException {
        if (userModel == null) {
            throw new IllegalArgumentException("UsuarioModel não pode ser null");
        }
        BeanUtils.copyProperties(userModel, this);
        this.senha = null;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserModel toModel() {
        if (id == null){
            return new UserModel(getNome(), getTelefone(), getEndereco(), getEmail(), getSenha(),isAdmin());
        }
        return new UserModel(this);
    }
}
