package com.stockwise.app.controllers;

import com.stockwise.app.dto.AtivoFinanceiroDto;
import com.stockwise.app.model.AtivoFinanceiroModel;
import com.stockwise.app.services.AtivoFinanceiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ativos-financeiros")
public class AtivoFinanceiroController {

    @Autowired
    private AtivoFinanceiroService service;

    // CREATE - Criar ativo financeiro
    @PostMapping
    public ResponseEntity<AtivoFinanceiroModel> criar(@RequestBody AtivoFinanceiroDto dto) {
        return ResponseEntity.ok(service.salvar(dto));
    }

    // READ - Listar todos os ativos financeiros
    @GetMapping
    public ResponseEntity<List<AtivoFinanceiroModel>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // READ - Buscar ativo financeiro por ID
    @GetMapping("/{id}")
    public ResponseEntity<AtivoFinanceiroModel> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // READ - Listar ativos financeiros por usuário
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<AtivoFinanceiroModel>> listarPorUsuario(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(service.listarPorUsuario(usuarioId));
    }

    // UPDATE - Atualizar ativo financeiro
    @PutMapping("/{id}")
    public ResponseEntity<AtivoFinanceiroModel> atualizar(@PathVariable Long id, @RequestBody AtivoFinanceiroDto dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    // DELETE - Deletar ativo financeiro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}