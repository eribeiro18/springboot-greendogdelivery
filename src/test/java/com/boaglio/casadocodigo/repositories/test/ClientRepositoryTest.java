package com.boaglio.casadocodigo.repositories.test;

import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.boaglio.casadocodigo.model.Cliente;
import com.boaglio.casadocodigo.repositories.ClienteRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientRepositoryTest {

	@Inject
	private ClienteRepository clienteRepository;
	
	@Test
	public void findAll() {
		List<Cliente> clientes = this.clienteRepository.findAll();
		
		boolean result = clientes != null && !clientes.isEmpty();
		Assert.assertEquals("CLIENTES CARREGADOS", true, result);
	}	
}
