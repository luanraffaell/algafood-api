package com.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algafood.domain.model.FormaPagamento;

public interface FormaDePagamentoRepository extends JpaRepository<FormaPagamento, Long> {
	
}
