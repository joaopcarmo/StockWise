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

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(UserDto::new).toList();
    }

    @Transactional(readOnly = true)
    public UserDto findById(UUID id) {
        return userRepository.findById(id)
                .map(UserDto::new)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    }

    @Transactional
    public UserDto save(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email já registrado");
        }
        UserModel userModel = userDto.toModel();
        userModel.setSenha(passwordEncoder.encode(userModel.getSenha()));
        return userRepository.save(userModel).toDto();
    }

    @Transactional
    public UserDto update(UUID id, UserDto userDtoAtualizado) {
        UserModel userExistente = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        userExistente.setNome(userDtoAtualizado.getNome());
        userExistente.setTelefone(userDtoAtualizado.getTelefone());
        userExistente.setEndereco(userDtoAtualizado.getEndereco());
        // Opcional: atualizar senha se for enviada
        if (userDtoAtualizado.getSenha() != null && !userDtoAtualizado.getSenha().isBlank()) {
            userExistente.setSenha(passwordEncoder.encode(userDtoAtualizado.getSenha()));
        }

        return userRepository.save(userExistente).toDto();
    }

    @Transactional(readOnly = true)
    public UserDto login(String email, String senha) {
        UserModel user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        if (!passwordEncoder.matches(senha, user.getSenha())) {
            throw new IllegalArgumentException("Credenciais inválidas");
        }

        return user.toDto();
    }

    @Transactional
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }
}
