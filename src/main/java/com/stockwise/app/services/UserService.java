package com.stockwise.app.services;

import com.stockwise.app.dto.UserDto;
import com.stockwise.app.model.UserModel;
import com.stockwise.app.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<UserDto> findAll(){
        return userRepository.findAll().stream().map(UserDto::new).toList();
    }

    @Transactional(readOnly = true)
    public UserDto findById(UUID id) {
        return userRepository.findById(id).map(UserDto::new).orElse(null);
    }

    @Transactional
    public UserDto save(UserDto userDto) {
        UserModel userModel = userDto.toModel();
        userModel.setSenha(passwordEncoder.encode(userModel.getSenha()));
        return userRepository.save(userModel).toDto();
    }

    @Transactional
    public UserDto update(UUID id, UserDto userDtoAtualizado){
        UserModel userExistente = userRepository.findById(id).orElse(null);
        if (userExistente != null) {
            userExistente.setNome(userDtoAtualizado.getNome());
            userExistente.setTelefone(userDtoAtualizado.getTelefone());
            userExistente.setEndereco(userDtoAtualizado.getEndereco());
            return userRepository.save(userExistente).toDto();
        }
        return null;
    }

    @Transactional
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }
}

