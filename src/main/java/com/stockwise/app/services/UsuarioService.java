package com.stockwise.app.services;

import com.stockwise.app.dto.UsuarioDto;
import com.stockwise.app.model.UsuarioModel;
import com.stockwise.app.repositories.UsuarioRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public List<UsuarioDto> findAll(){
        List<UsuarioModel> listaUsuarioModel = usuarioRepository.findAll();
        return listaUsuarioModel.stream().map(UsuarioDto::new).toList();
    }

    @Transactional(readOnly = true)
    public UsuarioDto findById(UUID id) {
        UsuarioModel usuarioModel = usuarioRepository.findById(id).orElse(null);

        if (usuarioModel == null) {
            return null;
        }
        return new UsuarioDto(usuarioModel);
    }

    @Transactional
    public UsuarioDto save(UsuarioDto usuarioDto) {
        return usuarioRepository.save(usuarioDto.toModel()).toDto();
    }


    @Transactional
    public UsuarioDto update (UUID id, UsuarioDto usuarioDtoAtualizado){
        UsuarioDto usuarioDtoExistente = findById(id);
        if (usuarioDtoExistente != null) {
            usuarioDtoExistente.setNome(usuarioDtoAtualizado.getNome());
            usuarioDtoExistente.setTelefone(usuarioDtoAtualizado.getTelefone());
            usuarioDtoExistente.setEndereco(usuarioDtoAtualizado.getEndereco());
            return save(usuarioDtoExistente);
        }
        return null;
    }

    @Transactional
    public void delete (UUID id) {
        usuarioRepository.deleteById(id);
    }



}
