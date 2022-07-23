package com.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algafood.domain.model.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial,BigDecimal taxaFinal);
	
	List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinhaId);
}
//não possui nenhum detalhe de qual é o mecanismo de persistencia de repositorio.
//também é chamado de repositorio orientado a persistência.

//https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation