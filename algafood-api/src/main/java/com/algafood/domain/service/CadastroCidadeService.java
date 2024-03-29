package com.algafood.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algafood.domain.exception.EntidadeEmUsoException;
import com.algafood.domain.exception.EstadoNaoEncontradaException;
import com.algafood.domain.exception.NegocioException;
import com.algafood.domain.model.Cidade;
import com.algafood.domain.model.Estado;
import com.algafood.domain.repository.CidadeRepository;
import com.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {
	

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Transactional
	public Cidade adicionar(Cidade cidade) {
		try {
			Estado estado = buscarEstado(cidade.getEstado().getId());
			cidade.setEstado(estado);
		return cidadeRepository.save(cidade);
		}catch(EstadoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	@Transactional
	public void deletar(Long id) {
		try {
		cidadeRepository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradaException(id);
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException("Cidade com id "+id+" se encontra em uso!");
		}
	}
	@Transactional
	public Cidade atualizar(Long id, Cidade cidade) {
		Cidade cidadeAtual = buscarOuFalhar(id);
		BeanUtils.copyProperties(cidade, cidadeAtual,"id");
		return adicionar(cidadeAtual);

		
	}
	private Estado buscarEstado(Long id) {
		return estadoRepository.findById(id).orElseThrow(() -> new EstadoNaoEncontradaException("Estado com id "+id+" não encontrado!"));

	}
	
	public Cidade buscarOuFalhar(Long id) {
		return cidadeRepository.findById(id).orElseThrow(() -> new CidadeNaoEncontradaException(id));
	}
	
}
