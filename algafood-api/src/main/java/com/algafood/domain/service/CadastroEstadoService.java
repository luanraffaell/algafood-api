package com.algafood.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algafood.domain.exception.EntidadeEmUsoException;
import com.algafood.domain.exception.EstadoNaoEncontradaException;
import com.algafood.domain.model.Estado;
import com.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Transactional
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}
	@Transactional
	public Estado atualizar(Estado estado) {
		Estado estadoAtual = estadoRepository.findById(estado.getId())
				.orElseThrow(() -> new EstadoNaoEncontradaException(estado.getId()));

		BeanUtils.copyProperties(estado, estadoAtual,"id");
		return estadoRepository.save(estadoAtual);
	}
	@Transactional
	public void remover(Long id) {
		try {
			estadoRepository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			throw new EstadoNaoEncontradaException(id);
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException("Entidade com o id "+id+" se encontra em uso!");
		}

	}
	public Estado buscarOuFalhar(Long id) {
		return estadoRepository.findById(id).orElseThrow(() -> new EstadoNaoEncontradaException(id));
	}
}
