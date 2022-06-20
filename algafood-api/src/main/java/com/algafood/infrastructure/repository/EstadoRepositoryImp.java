package com.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.transaction.annotation.Transactional;

import com.algafood.domain.model.Estado;
import com.algafood.domain.repository.EstadoRepository;

public class EstadoRepositoryImp implements EstadoRepository {
	private EntityManager manager;
	
	@Override
	public List<Estado> listar() {
		return manager.createQuery("from Estado",Estado.class).getResultList();
	}

	@Override
	public Estado buscar(Long id) {
		return manager.find(Estado.class, id);
	}
	
	@Transactional
	@Override
	public Estado salvar(Estado estado) {
		return manager.merge(estado);
	}
	
	@Transactional
	@Override
	public void remover(Estado estado) {
		estado = buscar(estado.getId());
		manager.remove(estado);
		
	}

}
