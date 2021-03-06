package com.algafood.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algafood.domain.exception.EntidadeEmUsoException;
import com.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algafood.domain.model.Estado;
import com.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}
	
	public Estado atualizar(Estado estado) {
		Estado estadoAtual = estadoRepository.findById(estado.getId()).get();
		if(estadoAtual == null) {
			throw new EntidadeNaoEncontradaException("Não existe um estado com o id:"+estado.getId());
		}
		BeanUtils.copyProperties(estado, estadoAtual,"id");
		return estadoRepository.save(estadoAtual);
	}
	
	public void remover(Long id) {
		try {
			estadoRepository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException("Não existe um estado com o id:"+id);
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException("Entidade com o id "+id+" se encontra em uso!");
		}

	}
}
