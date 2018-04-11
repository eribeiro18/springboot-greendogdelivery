package com.boaglio.casadocodigo.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.boaglio.casadocodigo.model.Cliente;
import com.boaglio.casadocodigo.model.Item;
import com.boaglio.casadocodigo.model.Pedido;
import com.boaglio.casadocodigo.repositories.ClienteRepository;
import com.boaglio.casadocodigo.repositories.PedidoRepository;

@Component
public class RepositoryTest implements ApplicationRunner {

	private static final Long CLIENTE_1 = 1l;	
	
	private static final Long ITEM_1 = 1000l;
	private static final Long ITEM_2 = 1001l;
	private static final Long ITEM_3 = 1002l;
		
	private static final Long PEDIDO = 1000l;	
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		Cliente cliente1 = new Cliente(CLIENTE_1,"MATEUS GOBO", "RUA TESTE 123");
		
		Item item1Pedido1 = new Item(ITEM_1, "GREEN DOG MEGA", 40.0);
		Item item2Pedido1 = new Item(ITEM_2, "GREEN DOG TRADICIONAL", 20.0);
		Item item3Pedido1 = new Item(ITEM_3, "GREEN DOG POSSANTE", 35.0);
		
		List<Item> itens = new ArrayList<>();
		itens.add(item1Pedido1);
		itens.add(item2Pedido1);
		itens.add(item3Pedido1);
		
		Pedido pedido = this.pedidoRepository.recuperarPedidosPorId(PEDIDO);		
		if(pedido != null) {
			pedido.setId(PEDIDO+1);
		}else{
			pedido = new Pedido();
			pedido.setId(PEDIDO);
		}
		pedido.setCliente(cliente1);
		pedido.setItens(itens);
		List<Pedido> pedidos = new ArrayList<>();
		pedidos.add(pedido);
		cliente1.setPedidos(pedidos);
		
		clienteRepository.saveAndFlush(cliente1);
		System.out.println("PEDIDO CLIENTE 1 REALIZADO...");		
	}		
}
