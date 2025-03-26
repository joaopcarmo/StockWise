package com.stockwise.app.controllers;

import java.util.List;
import java.util.UUID;

import com.stockwise.app.dto.FornecedorDto;
import com.stockwise.app.services.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @GetMapping
    public List<FornecedorDto> findAll() {
        return fornecedorService.findAll();
    }

    @GetMapping(value = "/{id}")
    public FornecedorDto findById(@PathVariable UUID id) {
        return fornecedorService.findById(id);
    }

    @PostMapping
    public FornecedorDto create(@RequestBody @Valid FornecedorDto fornecedorDto) {
        return fornecedorService.save(fornecedorDto);
    }

    @PutMapping("/{id}")
    public FornecedorDto update(@PathVariable UUID id, @RequestBody @Valid FornecedorDto fornecedorDtoAtualizado) {
        return fornecedorService.update(id, fornecedorDtoAtualizado);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        fornecedorService.delete(id);
    }
}
