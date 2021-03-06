package com.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.algafood.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {

	List<Restaurante> consultar(String nome,
			BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

}