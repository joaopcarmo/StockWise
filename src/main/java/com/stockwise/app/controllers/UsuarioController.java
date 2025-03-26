package com.stockwise.app.controllers;

import java.util.List;
import java.util.UUID;

import com.stockwise.app.dto.UsuarioDto;
import com.stockwise.app.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<UsuarioDto> findAll() {
        return usuarioService.findAll();
    }

    @GetMapping(value = "/{id}")
    public UsuarioDto findById(@PathVariable UUID id) {
        return usuarioService.findById(id);
    }

    @PostMapping
    public UsuarioDto create(@RequestBody @Valid UsuarioDto usuarioDto) {
        return usuarioService.save(usuarioDto);
    }

    @PutMapping("/{id}")
    public UsuarioDto update(@PathVariable UUID id, @RequestBody @Valid UsuarioDto usuarioDtoAtualizado) {
        return usuarioService.update(id, usuarioDtoAtualizado);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        usuarioService.delete(id);
    }
}
