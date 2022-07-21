package com.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algafood.domain.model.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Long>{
;
}
//não possui nenhum detalhe de qual é o mecanismo de persistencia de repositorio.
//também é chamado de repositorio orientado a persistência.