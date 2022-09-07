package com.algafood.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteDto {
	
	private Long id;
	private String nome;
	private BigDecimal taxaFrete;
	CozinhaDto cozinha;

}
