package com.boaglio.casadocodigo.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.boaglio.casadocodigo.model.Item;

@RepositoryRestResource(collectionResourceRel="itens",path="itens")
public interface ItemRepository extends JpaRepository<Item, Long>{

	@Query("Select i from Item i where i.id = :id ")
	Set<Item> recuperarItemPorId(@Param("id")Long id);
	
	@Query("select i from Item i where i.nome LIKE %:nome% ")
	Item recuperarItemPorNome(@Param("nome")String nome);
	
}
