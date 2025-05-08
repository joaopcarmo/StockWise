package com.stockwise.app.services;

import com.stockwise.app.dto.UserDto;
import com.stockwise.app.model.UserModel;
import com.stockwise.app.repositories.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<UserDto> findAll(){
        List<UserModel> listaUserModel = userRepository.findAll();
        return listaUserModel.stream().map(UserDto::new).toList();
    }

    @Transactional(readOnly = true)
    public UserDto findById(UUID id) {
        UserModel userModel = userRepository.findById(id).orElse(null);

        if (userModel == null) {
            return null;
        }
        return new UserDto(userModel);
    }

    @Transactional
    public UserDto save(UserDto userDto) {
        return userRepository.save(userDto.toModel()).toDto();
    }


    @Transactional
    public UserDto update (UUID id, UserDto userDtoAtualizado){
        UserDto userDtoExistente = findById(id);
        if (userDtoExistente != null) {
            userDtoExistente.setNome(userDtoAtualizado.getNome());
            userDtoExistente.setTelefone(userDtoAtualizado.getTelefone());
            userDtoExistente.setEndereco(userDtoAtualizado.getEndereco());
            return save(userDtoExistente);
        }
        return null;
    }

    @Transactional
    public void delete (UUID id) {
        userRepository.deleteById(id);
    }



}
