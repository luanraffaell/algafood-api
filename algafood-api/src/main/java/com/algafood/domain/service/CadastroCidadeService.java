package com.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import com.algafood.domain.exception.EntidadeEmUsoException;
import com.algafood.domain.exception.EntidadeNaoEncontradaException;
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
	
	public Cidade adicionar(Cidade cidade) {
		Estado estado = buscarEstado(cidade.getEstado().getId());
		cidade.setEstado(estado);
		return cidadeRepository.salvar(cidade);
	}
	
	public void deletar(Long id) {
		try {
		cidadeRepository.remover(id);
		}catch(InvalidDataAccessApiUsageException e) {
			throw new EntidadeNaoEncontradaException("Não foi encontrada uma cidade com o id:"+id);
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException("Cidade com id "+id+" se encontra em uso!");
		}
	}
	
	private Estado buscarEstado(Long id) {
		Estado estado = estadoRepository.buscar(id);
		if(estado == null) {
			throw new EntidadeNaoEncontradaException("Estado com id "+id+" não encontrado!");
		}
		return estado;
	}
	
}