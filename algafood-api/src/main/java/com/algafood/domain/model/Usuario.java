package com.algafood.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	private String email;
	private String senha;
	
	@CreationTimestamp
	private LocalDateTime dataCadatro;
	
	@ManyToMany
	@JoinTable(name="Usuario_grupo",joinColumns = @JoinColumn(name="usuario_id"), inverseJoinColumns = @JoinColumn(name="grupo_id"))
	private List<Grupo> grupos = new ArrayList<>();
}
