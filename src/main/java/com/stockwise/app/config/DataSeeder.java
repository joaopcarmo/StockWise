package com.stockwise.app.config;

import com.stockwise.app.model.UserModel;
import com.stockwise.app.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner loadData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Evita duplicar usuário se já existir

            if (userRepository.findByEmail("joao@email.com").isEmpty()) {
                UserModel user = new UserModel();
                user.setNome("João");
                user.setEmail("joao@email.com");
                user.setTelefone("31999999999");
                user.setEndereco("Rua Exemplo, 1234");
                user.setSenha(passwordEncoder.encode("joao1234"));

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date dataNascimento = sdf.parse("27/09/2003");
                user.setDataNascimento(dataNascimento);

                userRepository.save(user);
                System.out.println("Usuário joao@email.com criado com sucesso.");
            }
        };
    }
}
