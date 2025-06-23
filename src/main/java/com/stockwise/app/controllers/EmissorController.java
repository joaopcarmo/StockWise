package com.stockwise.app.controllers;

import com.stockwise.app.dto.EmissorDto;
import com.stockwise.app.model.EmissorModel;
import com.stockwise.app.services.EmissorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emissores")
public class EmissorController {

    @Autowired
    private EmissorService service;

    // CREATE - Criar emissor
    @PostMapping
    public ResponseEntity<EmissorModel> criar(@RequestBody EmissorDto dto) {
        return ResponseEntity.ok(service.salvar(dto));
    }

    // READ - Listar todos os emissores
    @GetMapping
    public ResponseEntity<List<EmissorModel>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // READ - Buscar emissor por ID
    @GetMapping("/{id}")
    public ResponseEntity<EmissorModel> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // UPDATE - Atualizar emissor
    @PutMapping("/{id}")
    public ResponseEntity<EmissorModel> atualizar(@PathVariable Long id, @RequestBody EmissorDto dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    // DELETE - Deletar emissor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}