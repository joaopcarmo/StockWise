package com.stockwise.app.controllers;

import com.stockwise.app.dto.HumorFinanceiroDto;
import com.stockwise.app.model.HumorFinanceiroModel.EstadoHumor;
import com.stockwise.app.security.jwt.JwtTokenProvider;
import com.stockwise.app.services.HumorFinanceiroService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/humor-financeiro")
public class HumorFinanceiroController {

    @Autowired
    private HumorFinanceiroService service;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private UUID getUsuarioIdFromRequest(HttpServletRequest request) {
        return jwtTokenProvider.getUserIdFromToken(jwtTokenProvider.resolveToken(request));
    }

    private boolean isAdmin(HttpServletRequest request) {
        return jwtTokenProvider.getIsAdminFromToken(jwtTokenProvider.resolveToken(request));
    }

    @GetMapping
    public ResponseEntity<?> findAll(HttpServletRequest request) {
        if (!isAdmin(request)) return ResponseEntity.status(403).body("Acesso negado");
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> findByUsuario(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(service.findByUsuarioId(usuarioId));
    }

    @GetMapping("/usuario/{usuarioId}/hoje")
    public ResponseEntity<?> findHoje(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(service.findHumorHoje(usuarioId));
    }

    @GetMapping("/usuario/{usuarioId}/periodo")
    public ResponseEntity<?> findByPeriodo(
            @PathVariable UUID usuarioId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return ResponseEntity.ok(service.findByPeriodo(usuarioId, inicio, fim));
    }

    @GetMapping("/usuario/{usuarioId}/estatisticas")
    public ResponseEntity<?> getEstatisticas(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(service.obterEstatisticasPorUsuario(usuarioId));
    }

    @PostMapping
    public ResponseEntity<?> createOrUpdateHoje(@Valid @RequestBody HumorFinanceiroDto dto, HttpServletRequest request) {
        UUID usuarioId = getUsuarioIdFromRequest(request);
        return ResponseEntity.ok(service.updateByUsuarioId(usuarioId, dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @Valid @RequestBody HumorFinanceiroDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estados-humor")
    public ResponseEntity<?> getEstadosHumor() {
        return ResponseEntity.ok(EstadoHumor.values());
    }
}