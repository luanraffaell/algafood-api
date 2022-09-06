package com.algafood;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.algafood.domain.exception.EntidadeEmUsoException;
import com.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algafood.domain.model.Cozinha;
import com.algafood.domain.service.CadastroCozinhaService;

@SpringBootTest
class CadastroCozinhaIntegrationTest {
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Test
	public void testarCadastroCozinhaComSucesso() {
		//cenario
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");
		
		//ação
		novaCozinha = cadastroCozinha.salvar(novaCozinha);
		
		//validação
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}
	
	@Test
	public void testarCadastroCozinhaSemNome() {		
		assertThrows(ConstraintViolationException.class,() ->{
			Cozinha novaCozinha = new Cozinha();
			novaCozinha.setNome(null);
			cadastroCozinha.salvar(novaCozinha);
		});
		
	}
	
	@Test
	public void deveFalhar_quandoExcluirCozinhaEmUso(){
		assertThrows(EntidadeEmUsoException.class, () -> {
			cadastroCozinha.excluir(1L);
		});
	}
	@Test
	public void deveFalhar_QuandoCozinhaInexistente() {
		assertThrows(EntidadeNaoEncontradaException.class,() -> {
			cadastroCozinha.excluir(10l);
		});
		
		
	}
}
