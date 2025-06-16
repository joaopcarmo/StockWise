package com.stockwise.app.model;

import com.stockwise.app.dto.HumorFinanceiroDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "humor_financeiro")
public class HumorFinanceiroModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime data;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoHumor estado;

    @NotNull
    @Column(name = "usuario_id", nullable = false)
    private UUID usuarioId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", insertable = false, updatable = false)
    private UserModel usuario;

    public HumorFinanceiroModel() {}

    public HumorFinanceiroModel(LocalDateTime data, EstadoHumor estado, UUID usuarioId) {
        this.data = data;
        this.estado = estado;
        this.usuarioId = usuarioId;
    }

    public HumorFinanceiroModel(HumorFinanceiroDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("HumorFinanceiroDto não pode ser null");
        }
        if (dto.getId() != null) {
            this.id = dto.getId();
        }
        this.data = dto.getData() != null ? dto.getData() : LocalDateTime.now();
        this.estado = dto.getEstado();
        this.usuarioId = dto.getUsuarioId();
    }

    @PrePersist
    protected void onCreate() {
        if (data == null) {
            data = LocalDateTime.now();
        }
    }

    // Getters e setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }
    public EstadoHumor getEstado() { return estado; }
    public void setEstado(EstadoHumor estado) { this.estado = estado; }
    public UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }
    public UserModel getUsuario() { return usuario; }
    public void setUsuario(UserModel usuario) { this.usuario = usuario; }

    public HumorFinanceiroDto toDto() {
        return new HumorFinanceiroDto(getId(), getData(), getEstado(), getUsuarioId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HumorFinanceiroModel that = (HumorFinanceiroModel) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public enum EstadoHumor {
        MUITO_PESSIMO("Muito Péssimo", "😰"),
        PESSIMO("Péssimo", "😞"),
        RUIM("Ruim", "😔"),
        NEUTRO("Neutro", "😐"),
        BOM("Bom", "😊"),
        MUITO_BOM("Muito Bom", "😄"),
        EXCELENTE("Excelente", "🤑");

        private final String descricao;
        private final String emoji;

        EstadoHumor(String descricao, String emoji) {
            this.descricao = descricao;
            this.emoji = emoji;
        }

        public String getDescricao() {
            return descricao;
        }

        public String getEmoji() {
            return emoji;
        }

        public String getDescricaoCompleta() {
            return emoji + " " + descricao;
        }
    }
}