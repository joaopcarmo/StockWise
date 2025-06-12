package com.stockwise.app.model;

import com.stockwise.app.dto.UserDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "usuario")
public class UserModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @NotNull
    @Column
    private String nome;

    @NotNull
    @Column(unique = true)
    private String telefone;

    @NotNull
    @NotBlank
    @Column(unique = true)
    private String email;

    @NotNull
    @NotBlank
    private String senha;

    @NotNull
    private boolean admin = false;

    @NotNull
    private String endereco;

    @NotNull
    private Date dataNascimento;

    public UserModel() {}

    public UserModel(String nome, String telefone, String endereco, String email, String senha, Date dataNascimento, boolean admin) {
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.email = email;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
        this.admin = admin;
    }

    public UserModel(UUID id, String nome, String telefone, String endereco, String email, String senha, Date dataNascimento, boolean admin) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.email = email;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
        this.admin = admin;
    }

    public UserModel(UserDto userDto) {
        if (userDto == null) {
            throw new IllegalArgumentException("UsuarioDto não pode ser null");
        }
        if (userDto.getId() != null) {
            this.id = userDto.getId();
        }
        this.nome = userDto.getNome();
        this.telefone = userDto.getTelefone();
        this.endereco = userDto.getEndereco();
        this.email = userDto.getEmail();
        this.senha = userDto.getSenha();
        this.dataNascimento = userDto.getDataNascimento();
        this.admin = userDto.isAdmin();
    }

    // Getters e setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public boolean isAdmin() { return admin; }
    public void setAdmin(boolean admin) { this.admin = admin; }
    public Date getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(Date dataNascimento) { this.dataNascimento = dataNascimento; }

    public UserDto toDto() {
        return new UserDto(getId(), getNome(), getTelefone(), getEndereco(), getEmail(), getSenha(), getDataNascimento(), isAdmin());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel that = (UserModel) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
