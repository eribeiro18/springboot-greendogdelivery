package com.boaglio.casadocodigo.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, 
	      		  property = "id")
public class Cliente implements Serializable{

	private static final long serialVersionUID = 675760451142263997L;
	private static final String MESSAGE_VALIDATE = "O tamanho do endereço deve ser entre {min} e {max} caracteres";		

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Length(min=2,max=30,message=MESSAGE_VALIDATE)
	private String nome;
	
	@NotNull
	@Length(min=2,max=300,message=MESSAGE_VALIDATE)
	private String endereco;
	
	@JsonManagedReference
	@OneToMany(mappedBy="cliente", fetch=FetchType.EAGER, cascade={CascadeType.ALL})
	private List<Pedido> pedidos;

	public Cliente() {}
	public Cliente(Long id, String nome, String endereco) {		
		this.id = id;
		this.nome = nome;
		this.endereco = endereco;		
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

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}		
}
