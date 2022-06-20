package com.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.transaction.annotation.Transactional;

import com.algafood.domain.model.Permissao;
import com.algafood.domain.repository.PermissaoRepository;

public class PermissaoRepositoryImp implements PermissaoRepository {
	private EntityManager manager;
	
	@Override
	public List<Permissao> listar() {
		return manager.createQuery("from Permissao",Permissao.class).getResultList();
	}

	@Override
	public Permissao buscar(Long id) {
		return manager.find(Permissao.class, id);
	}
	
	@Transactional
	@Override
	public Permissao salvar(Permissao permissao) {
		return manager.merge(permissao);
	}
	
	@Transactional
	@Override
	public void remover(Permissao permissao) {
		permissao = buscar(permissao.getId());
		manager.remove(permissao);
		
	}

}
