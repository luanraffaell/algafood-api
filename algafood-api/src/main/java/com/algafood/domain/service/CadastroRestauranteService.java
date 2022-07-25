package com.algafood.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algafood.domain.exception.EntidadeEmUsoException;
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
		return restauranteRepository.save(restaurante);
	}
	
	public Restaurante atualizar(Restaurante restaurante) {
		Cozinha cozinhaAtual = buscarCozinha(restaurante.getCozinha().getId());
		Restaurante restauranteAtual = restauranteRepository.findById(restaurante.getId())
				.orElseThrow(()-> new EntidadeNaoEncontradaException("Não existe um restaurante com id:"+restaurante.getId()));
		restaurante.setCozinha(cozinhaAtual);
		BeanUtils.copyProperties(restaurante, restauranteAtual,"id","formasPagamento","endereco","dataCadastro");
		return salvar(restauranteAtual);
	}
	public Restaurante remover(Long id) {
		Restaurante restauranteAtual = restauranteRepository.findById(id)
				.orElseThrow(()-> new EntidadeNaoEncontradaException("Não existe um restaurante com id:"+id));
		try {
		restauranteRepository.deleteById(id);
		return restauranteAtual;
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException("Restaurante com id "+id+" não pode ser excluido pois está em uso!");
		}
	}
	
	private Cozinha buscarCozinha(Long id) {
		Cozinha cozinha = cozinhaRepository.findById(id).orElseThrow(()-> new EntidadeNaoEncontradaException("Não existe cadastro de cozinha com o código:"+id));
		return cozinha;
	}
	
}
