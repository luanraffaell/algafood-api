package com.algafood.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algafood.domain.model.Cozinha;
import com.algafood.domain.model.Restaurante;
import com.algafood.domain.repository.CozinhaRepository;
import com.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = buscarCozinha(cozinhaId);
		
		restaurante.setCozinha(cozinha);
		return restauranteRepository.salvar(restaurante);
	}
	
	public Restaurante atualizar(Restaurante restaurante) {
		Cozinha cozinhaAtual = buscarCozinha(restaurante.getCozinha().getId());
		Restaurante restauranteAtual = restauranteRepository.buscar(restaurante.getId());
		restaurante.setCozinha(cozinhaAtual);
		BeanUtils.copyProperties(restaurante, restauranteAtual,"id");
		return salvar(restauranteAtual);
	}
	
	private Cozinha buscarCozinha(Long id) {
		Cozinha cozinha = cozinhaRepository.buscar(id);
		if(cozinha == null) {
			throw new EntidadeNaoEncontradaException("Não existe cadastro de cozinha com o código:"+id);
		}
		return cozinha;
	}
}
