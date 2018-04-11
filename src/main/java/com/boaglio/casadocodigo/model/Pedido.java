package com.boaglio.casadocodigo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, 
  				  property = "id")
public class Pedido implements Serializable{

	private static final long serialVersionUID = 152728477168223616L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;		
	
	@DateTimeFormat(pattern="dd-MM-yyyy")	
	private Date data;
	
	@Min(1)
	private Double valorTotal;
	
	@ManyToOne(optional=true)
	private Cliente cliente;
	
	@ManyToMany(cascade={CascadeType.MERGE})
	@JoinTable(name="PEDIDO_ITENS",
			   joinColumns = {@JoinColumn(name="ITENS_ID")},
			   inverseJoinColumns = {@JoinColumn(name="CLIENTE_ID")})
	private List<Item> itens;

	public Pedido() {}
	public Pedido(Long id, Date data, Double valorTotal) {
		super();
		this.id = id;
		this.data = data;
		this.valorTotal = valorTotal;				
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Item> getItens() {
		return itens;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}		
}
