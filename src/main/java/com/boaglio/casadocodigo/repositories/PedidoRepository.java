package com.boaglio.casadocodigo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.boaglio.casadocodigo.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{

	@Query("SELECT p FROM Pedido p WHERE p.id = :id ")
	public Pedido recuperarPedidosPorId(@Param("id")Long id);
	
	@Query("SELECT p FROM Pedido p JOIN FETCH p.cliente ")
	public List<Pedido> recuperarPedidos();
}
