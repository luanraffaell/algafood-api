package com.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algafood.api.assembler.RestauranteModelAssembler;
import com.algafood.api.model.RestauranteDto;
import com.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algafood.domain.model.Cozinha;
import com.algafood.domain.model.Restaurante;
import com.algafood.domain.model.input.RestauranteInput;
import com.algafood.domain.repository.RestauranteRepository;
import com.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private RestauranteModelAssembler restauranteModelAssembler;
	
	@GetMapping
	public List<RestauranteDto> listar(){
		return restauranteModelAssembler.toCollectionDto(restauranteRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public RestauranteDto buscar(@PathVariable Long id){
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(id);
		return restauranteModelAssembler.toModel(restaurante);
	}

	
	@PostMapping
	public ResponseEntity<?>adicionar(@RequestBody @Valid RestauranteInput restauranteIntput){
		try {
			Restaurante restaurante = toDomainObject(restauranteIntput);
			RestauranteDto restauranteDto = restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restaurante));
			return ResponseEntity.status(HttpStatus.CREATED).body(restauranteDto);
		} catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public RestauranteDto atualizar(@PathVariable Long id, @RequestBody @Valid Restaurante restaurante){
		restaurante.setId(id);
		return restauranteModelAssembler.toModel(cadastroRestaurante.atualizar(restaurante));
	}
	
	@PatchMapping("/{id}")
	public RestauranteDto atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos, HttpServletRequest request){
		Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(id);
		merge(campos,restauranteAtual,request);
		return atualizar(id,restauranteAtual);
		
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id){
			cadastroRestaurante.remover(id);
	}

	
	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino, HttpServletRequest request) {
		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
		try {
			ObjectMapper objectMapper = new ObjectMapper(); // responsável pela serialização no jackson
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
			
			dadosOrigem.forEach((nomePropriedade,valorPropriedade)->{
				Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
				field.setAccessible(true);
				
				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
				ReflectionUtils.setField(field, restauranteDestino, novoValor);
			});
		}catch(IllegalArgumentException e) {
			Throwable rootCause = e.getCause();
			throw new HttpMessageNotReadableException(e.getMessage(),rootCause,serverHttpRequest);
		}
	}

	private Restaurante toDomainObject(RestauranteInput restauranteInput) {
		Restaurante restaurante = new Restaurante();
		restaurante.setNome(restauranteInput.getNome());
		restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());
		Cozinha cozinha = new Cozinha();
		cozinha.setId(restauranteInput.getCozinha().getId());
		restaurante.setCozinha(cozinha);
		return restaurante;
		
		
	}
}
