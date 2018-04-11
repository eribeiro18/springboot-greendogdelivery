package com.boaglio.casadocodigo.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, 
  				  property = "id")
public class Item implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5821983429006841523L;
	private static final String MESSAGE_VALIDATE = "O nome do item deve ser entre {min} e {max} caracteres";
	private static final String MESSAGE_VALIDATE_PRICE = "O valor minimo deve ser {value} reais";
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;	
	
	@NotNull
	@Length(min=2,max=30,message=MESSAGE_VALIDATE)
	private String nome;
	
	@NotNull(message="Informe o preço")
	@Min(value=20,message=MESSAGE_VALIDATE_PRICE)
	private Double preco;

	public Item() {}
	public Item(Long id, String nome, Double preco) {
		this.id = id;
		this.nome = nome;
		this.preco = preco;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}		
}
