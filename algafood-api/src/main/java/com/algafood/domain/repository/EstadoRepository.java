package com.algafood.domain.repository;

import java.util.List;

import com.algafood.domain.model.Estado;

public interface EstadoRepository {
	List<Estado> listar();
	Estado buscar(Long id);
	Estado salvar(Estado estado);
	void remover(Estado estado);
}
//não possui nenhum detalhe de qual é o mecanismo de persistencia de repositorio.
//também é chamado de repositorio orientado a persistência.