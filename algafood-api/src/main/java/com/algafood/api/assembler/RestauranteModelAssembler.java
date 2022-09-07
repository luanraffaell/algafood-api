package com.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.algafood.api.model.CozinhaDto;
import com.algafood.api.model.RestauranteDto;
import com.algafood.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler {
	
	public RestauranteDto toModel(Restaurante restaurante) {
		RestauranteDto restauranteDto = new RestauranteDto();
		CozinhaDto cozinhaDto = new CozinhaDto();
		cozinhaDto.setId(restaurante.getCozinha().getId());
		cozinhaDto.setNome(restaurante.getCozinha().getNome());
		
		restauranteDto.setId(restaurante.getId());
		restauranteDto.setNome(restaurante.getNome());
		restauranteDto.setTaxaFrete(restaurante.getTaxaFrete());
		restauranteDto.setCozinha(cozinhaDto);
		return restauranteDto;
	}
	public List<RestauranteDto> toCollectionDto(List<Restaurante> restaurantes){
		return restaurantes.stream()
				.map(restaurante -> toModel(restaurante))
				.collect(Collectors.toList());
	}
}
