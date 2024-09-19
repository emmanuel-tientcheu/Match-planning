package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(PostgreSQLTestConfiguration.class)
@SpringBootTest
class EsportsClashByAncryApplicationTests {

	@Test
	void contextLoads() {
	}

}
