package com.stockwise.app.dto;

import com.stockwise.app.model.HumorFinanceiroModel;
import com.stockwise.app.model.HumorFinanceiroModel.EstadoHumor;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import java.time.LocalDateTime;
import java.util.UUID;

public class HumorFinanceiroDto {

    @Null(message = "O ID deve ser nulo na criação")
    private UUID id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime data;

    @NotNull(message = "Estado do humor é obrigatório")
    private EstadoHumor estado;

    @NotNull(message = "ID do usuário é obrigatório")
    private UUID usuarioId;

    private String estadoDescricao;
    private String estadoEmoji;

    public HumorFinanceiroDto() {}

    public HumorFinanceiroDto(UUID id, LocalDateTime data, EstadoHumor estado, UUID usuarioId) {
        this.id = id;
        this.data = data;
        this.estado = estado;
        this.usuarioId = usuarioId;
        preencherDescricaoEmoji(estado);
    }

    public HumorFinanceiroDto(LocalDateTime data, EstadoHumor estado, UUID usuarioId) {
        this(null, data, estado, usuarioId);
    }

    public HumorFinanceiroDto(HumorFinanceiroModel model) {
        if (model == null) {
            throw new IllegalArgumentException("HumorFinanceiroModel não pode ser null");
        }
        this.id = model.getId();
        this.data = model.getData();
        this.estado = model.getEstado();
        this.usuarioId = model.getUsuarioId();
        preencherDescricaoEmoji(this.estado);
    }

    private void preencherDescricaoEmoji(EstadoHumor estado) {
        if (estado != null) {
            this.estadoDescricao = estado.getDescricao();
            this.estadoEmoji = estado.getEmoji();
        }
    }

    public HumorFinanceiroModel toModel() {
        return new HumorFinanceiroModel(this);
    }

    // Getters e Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }

    public EstadoHumor getEstado() { return estado; }
    public void setEstado(EstadoHumor estado) {
        this.estado = estado;
        preencherDescricaoEmoji(estado);
    }

    public UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }

    public String getEstadoDescricao() { return estadoDescricao; }
    public void setEstadoDescricao(String estadoDescricao) { this.estadoDescricao = estadoDescricao; }

    public String getEstadoEmoji() { return estadoEmoji; }
    public void setEstadoEmoji(String estadoEmoji) { this.estadoEmoji = estadoEmoji; }
}