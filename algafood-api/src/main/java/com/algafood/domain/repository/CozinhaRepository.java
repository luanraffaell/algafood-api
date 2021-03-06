package com.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algafood.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {
	//List<Cozinha> consultarPorNome(String nome);

}
//não possui nenhum detalhe de qual é o mecanismo de persistencia de repositorio.
//também é chamado de repositorio orientado a persistência.