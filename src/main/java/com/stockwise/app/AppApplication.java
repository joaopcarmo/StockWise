/**
 * Documentação no Swagger:
 * 1) http://localhost:8080/swagger-ui/index.html
 * ou
 * 2) http://localhost:8080/swagger-ui.html
 *
 * Console do Banco de Dados em Memória H2:
 * 1) http://localhost:8080/h2-console
 */


package com.stockwise.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

}
