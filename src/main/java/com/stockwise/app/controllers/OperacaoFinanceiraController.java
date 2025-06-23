package com.stockwise.app.controllers;

import com.stockwise.app.dto.OperacaoFinanceiraDto;
import com.stockwise.app.model.OperacaoFinanceiraModel;
import com.stockwise.app.services.OperacaoFinanceiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/operacoes-financeiras")
public class OperacaoFinanceiraController {

    @Autowired
    private OperacaoFinanceiraService service;

    @PostMapping
    public ResponseEntity<OperacaoFinanceiraModel> criar(@RequestBody OperacaoFinanceiraDto dto) {
        OperacaoFinanceiraModel nova = service.salvar(dto);
        return ResponseEntity.ok(nova);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<OperacaoFinanceiraModel>> listarPorUsuario(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(service.listarPorUsuario(usuarioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperacaoFinanceiraModel> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OperacaoFinanceiraModel> atualizar(@PathVariable Long id, @RequestBody OperacaoFinanceiraDto dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
