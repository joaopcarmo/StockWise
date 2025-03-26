package com.stockwise.app.dto;

import com.stockwise.app.model.FornecedorModel;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

import org.springframework.beans.BeanUtils;

public class FornecedorDto {
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

    public FornecedorDto() {
    }

    public FornecedorDto(UUID id, String nome, String telefone, String endereco) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public FornecedorDto(FornecedorModel fornecedorModel) throws IllegalArgumentException {
        if (fornecedorModel == null) {
            throw new IllegalArgumentException("FornecedorModel não pode ser null");
        }
        BeanUtils.copyProperties(fornecedorModel, this);
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

    public FornecedorModel toModel() {
        if (id == null){
            return new FornecedorModel(getNome(), getTelefone(), getEndereco());
        }
        return new FornecedorModel(this);
    }
}
