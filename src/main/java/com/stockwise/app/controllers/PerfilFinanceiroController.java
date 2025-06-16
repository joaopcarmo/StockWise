package com.stockwise.app.controllers;

import com.stockwise.app.dto.PerfilFinanceiroDto;
import com.stockwise.app.security.jwt.JwtTokenProvider;
import com.stockwise.app.services.PerfilFinanceiroService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/perfil-financeiro")
public class PerfilFinanceiroController {

    @Autowired
    private PerfilFinanceiroService service;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private UUID getUsuarioIdFromRequest(HttpServletRequest request) {
        return jwtTokenProvider.getUserIdFromToken(jwtTokenProvider.resolveToken(request));
    }

    private boolean isAdmin(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        return Boolean.TRUE.equals(jwtTokenProvider.getIsAdminFromToken(token));
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
    public ResponseEntity<?> findByUsuarioId(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(service.findByUsuarioId(usuarioId));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody PerfilFinanceiroDto dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @Valid @RequestBody PerfilFinanceiroDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/meu")
    public ResponseEntity<?> getMeuPerfil(HttpServletRequest request) {
        UUID usuarioId = getUsuarioIdFromRequest(request);
        return ResponseEntity.ok(service.findByUsuarioId(usuarioId));
    }

    @PutMapping("/meu")
    public ResponseEntity<?> updateMeuPerfil(HttpServletRequest request, @Valid @RequestBody PerfilFinanceiroDto dto) {
        UUID usuarioId = getUsuarioIdFromRequest(request);
        dto.setUsuarioId(usuarioId);
        return ResponseEntity.ok(service.updateByUsuarioId(usuarioId, dto));
    }
}