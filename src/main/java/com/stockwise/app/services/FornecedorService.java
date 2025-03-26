package com.stockwise.app.services;

import com.stockwise.app.dto.FornecedorDto;
import com.stockwise.app.model.FornecedorModel;
import com.stockwise.app.repositories.FornecedorRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FornecedorService {

    @Autowired
    private final FornecedorRepository fornecedorRepository;

    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    @Transactional(readOnly = true)
    public List<FornecedorDto> findAll(){
        List<FornecedorModel> listaFornecedorModel = fornecedorRepository.findAll();
        return listaFornecedorModel.stream().map(FornecedorDto::new).toList();
    }

    @Transactional(readOnly = true)
    public FornecedorDto findById(UUID id) {
        FornecedorModel fornecedorModel = fornecedorRepository.findById(id).orElse(null);

        if (fornecedorModel == null) {
            return null;
        }
        return new FornecedorDto(fornecedorModel);
    }

    @Transactional
    public FornecedorDto save(FornecedorDto fornecedorDto) {
        return fornecedorRepository.save(fornecedorDto.toModel()).toDto();
    }


    @Transactional
    public FornecedorDto update (UUID id, FornecedorDto fornecedorDtoAtualizado){
        FornecedorDto fornecedorDtoExistente = findById(id);
        if (fornecedorDtoExistente != null) {
            fornecedorDtoExistente.setNome(fornecedorDtoAtualizado.getNome());
            fornecedorDtoExistente.setTelefone(fornecedorDtoAtualizado.getTelefone());
            fornecedorDtoExistente.setEndereco(fornecedorDtoAtualizado.getEndereco());
            return save(fornecedorDtoExistente);
        }
        return null;
    }

    @Transactional
    public void delete (UUID id) {
        fornecedorRepository.deleteById(id);
    }



}
