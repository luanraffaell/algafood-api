package com.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algafood.api.model.RestauranteDto;
import com.algafood.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler {
	@Autowired
	private ModelMapper modelMapper;
	
	public RestauranteDto toModel(Restaurante restaurante) {
		return modelMapper.map(restaurante, RestauranteDto.class);
	}
	public List<RestauranteDto> toCollectionDto(List<Restaurante> restaurantes){
		return restaurantes.stream()
				.map(restaurante -> toModel(restaurante))
				.collect(Collectors.toList());
	}
}
