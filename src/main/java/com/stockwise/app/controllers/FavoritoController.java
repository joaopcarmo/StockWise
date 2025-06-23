package com.stockwise.app.controllers;

import com.stockwise.app.dto.FavoritoDto;
import com.stockwise.app.model.FavoritoModel;
import com.stockwise.app.services.FavoritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/favoritos")
public class FavoritoController {

    @Autowired
    private FavoritoService service;

    // CREATE - Criar favorito
    @PostMapping
    public ResponseEntity<FavoritoModel> criar(@RequestBody FavoritoDto dto) {
        return ResponseEntity.ok(service.salvar(dto));
    }

    // READ - Listar todos os favoritos
    @GetMapping
    public ResponseEntity<List<FavoritoModel>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // READ - Buscar favorito por ID
    @GetMapping("/{id}")
    public ResponseEntity<FavoritoModel> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // READ - Listar favoritos por usuário
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<FavoritoModel>> listarPorUsuario(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(service.listarPorUsuario(usuarioId));
    }

    // UPDATE - Atualizar favorito
    @PutMapping("/{id}")
    public ResponseEntity<FavoritoModel> atualizar(@PathVariable Long id, @RequestBody FavoritoDto dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    // DELETE - Deletar favorito
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}