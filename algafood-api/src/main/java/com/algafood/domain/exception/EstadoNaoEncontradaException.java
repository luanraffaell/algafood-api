package com.algafood.domain.exception;

public class EstadoNaoEncontradaException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;
	
	public EstadoNaoEncontradaException(String mensagem) {
		super(mensagem);		
	}
	public EstadoNaoEncontradaException(Long id) {
		this(String.format("Não existe um estado com o id: %d", id));	
	}


}
