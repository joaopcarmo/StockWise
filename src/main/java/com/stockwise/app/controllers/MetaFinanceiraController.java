package com.stockwise.app.controllers;

import com.stockwise.app.dto.MetaFinanceiraDto;
import com.stockwise.app.security.jwt.JwtTokenProvider;
import com.stockwise.app.services.MetaFinanceiraService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/meta-financeira")
public class MetaFinanceiraController {

    @Autowired
    private MetaFinanceiraService service;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private UUID getUsuarioId(HttpServletRequest request) {
        return jwtTokenProvider.getUserIdFromToken(jwtTokenProvider.resolveToken(request));
    }

    @GetMapping("/meus")
    public ResponseEntity<?> listarMinhasMetas(HttpServletRequest request) {
        return ResponseEntity.ok(service.findByUsuario(getUsuarioId(request)));
    }

    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody MetaFinanceiraDto dto, HttpServletRequest request) {
        dto.setUsuarioId(getUsuarioId(request));
        return ResponseEntity.ok(service.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable UUID id, @Valid @RequestBody MetaFinanceiraDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}