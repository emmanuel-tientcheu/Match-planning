package com.example.demo;

import org.springframework.boot.SpringApplication;

public class TestEsportsClashByAncryApplication {

	public static void main(String[] args) {
		SpringApplication.from(EsportsClashByAncryApplication::main).with(PostgreSQLTestConfiguration.class).run(args);
	}

}
