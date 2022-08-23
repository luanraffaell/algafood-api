package com.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algafood.domain.exception.EntidadeEmUsoException;
import com.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algafood.domain.model.Cidade;
import com.algafood.domain.model.Estado;
import com.algafood.domain.repository.CidadeRepository;
import com.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {
	
	private static final String MSG_CIDADE_NAO_ENCONTRADA = "Não foi encontrada uma cidade com o id:";

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Cidade adicionar(Cidade cidade) {
		Estado estado = buscarEstado(cidade.getEstado().getId());
		cidade.setEstado(estado);
		return cidadeRepository.save(cidade);
	}
	
	public void deletar(Long id) {
		try {
		cidadeRepository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(MSG_CIDADE_NAO_ENCONTRADA+id);
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException("Cidade com id "+id+" se encontra em uso!");
		}
	}
	
	private Estado buscarEstado(Long id) {
		return estadoRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException("Estado com id "+id+" não encontrado!"));

	}
	
	public Cidade buscarOuFalhar(Long id) {
		return cidadeRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(MSG_CIDADE_NAO_ENCONTRADA));
	}
	
}
