package com.algafood.domain.repository;

import java.util.List;

import com.algafood.domain.model.Cozinha;

public interface CozinhaRepository {
	List<Cozinha> listar();
	Cozinha buscar(Long id);
	Cozinha salvar(Cozinha cozinha);
	void remover(Long id);
}
//não possui nenhum detalhe de qual é o mecanismo de persistencia de repositorio.
//também é chamado de repositorio orientado a persistência.