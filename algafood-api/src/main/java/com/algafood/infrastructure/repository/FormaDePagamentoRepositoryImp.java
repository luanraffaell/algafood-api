package com.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import com.algafood.domain.model.FormaPagamento;
import com.algafood.domain.repository.FormaDePagamentoRepository;

public class FormaDePagamentoRepositoryImp implements FormaDePagamentoRepository {
	
	private EntityManager manager;
	@Override
	public List<FormaPagamento> listar() {
		TypedQuery<FormaPagamento> query = manager.createQuery("from FormaDePagamento",FormaPagamento.class);
		return query.getResultList();
	}

	@Override
	public FormaPagamento buscar(Long id) {
		return manager.find(FormaPagamento.class, id);
	}
	
	@Transactional
	@Override
	public FormaPagamento salvar(FormaPagamento forma) {
		return manager.merge(forma);
	}
	
	@Transactional
	@Override
	public void remover(FormaPagamento forma) {
		forma = buscar(forma.getId());
		manager.remove(forma);
		
	}

}
