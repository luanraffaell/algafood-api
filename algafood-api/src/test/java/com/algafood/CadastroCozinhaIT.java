package com.algafood;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CadastroCozinhaIT {
	@LocalServerPort
	private int port;
	
	@Test
	public void deveRetornarStatus200QuandoConsultarCozinhas() {
		RestAssured.given()
			.basePath("/cozinhas")
			.port(port)
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(200);
	}
	@Test
	public void deveConter4Cozinhas_QuandoConsultarCozinhas() {
		RestAssured.given()
		.basePath("/cozinhas")
		.port(port)
		.accept(ContentType.JSON)
	.when()
		.get()
	.then()
		.body("", Matchers.hasSize(4))
		.body("nome", Matchers.hasItems("Indiana","Tailandesa"));
	}
	
}

	
