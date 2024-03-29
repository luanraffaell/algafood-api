package com.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algafood.domain.model.Restaurante;
import com.algafood.domain.repository.RestauranteRepository;
import com.algafood.infrastructure.repository.spec.RestauranteSpecs;

@RestController
@RequestMapping("/teste")
public class TestController {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	/*
	 * @GetMapping("/cozinhas/por-nome") public List<Cozinha>
	 * cozinhasPorNome(@RequestParam String nome){ return
	 * cozinhaRepository.consultarPorNome(nome); }
	 */
	
	@GetMapping("/restaurante/por-taxa-frete")
	public List<Restaurante> restaurantesPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal){
		return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
	}
	//localhost:8080/teste/restaurante/por-taxa-frete?taxaInicial=1&taxaFinal=100
	//taxas são inseridas no param do postman
	
	@GetMapping("/restaurante/por-nome")
	public List<Restaurante> restaurantesPorTaxaFrete(String nome, Long cozinhaId){
		return restauranteRepository.consultaPorNome(nome, cozinhaId);
	}
	
	@GetMapping("/restaurante/por-nome-e-frete")
	public List<Restaurante> restaurantesPorNomeEFrete(String nome,BigDecimal taxaFreteInicial,BigDecimal taxaFreteFinal){
		return restauranteRepository.consultar(nome,taxaFreteInicial, taxaFreteFinal);
	}
	
	@GetMapping("/restaurante/com-frete-gratis")
	public List<Restaurante> restaurantesComFreteGratis(String nome){
		
		return restauranteRepository.findAll(RestauranteSpecs.comFreteGratis().and(RestauranteSpecs.comNomeSemelhante(nome)));
	}
}
