package com.boaglio.casadocodigo.repositories.test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GreenDogDeliveryApplicationTests {

	@Inject
	private WebApplicationContext context;	
	private MockMvc mockMvc;
	private final String URL_API = "/api";
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	@Test
	public void testHome() throws Exception {
		System.out.println(this.mockMvc.perform(get(URL_API)).andDo(print()));
		this.mockMvc.perform(get(URL_API)).andExpect(status().isOk())
										  .andExpect(content().string(containsString("clientes")));
	}
	
	@Test
	public void findItem2() throws Exception {
		System.out.println(this.mockMvc.perform(get(URL_API)).andDo(print()));
		this.mockMvc.perform(get(URL_API+"/itens/2"))
					.andExpect(status().isOk())
				    .andExpect(jsonPath("preco", equalTo(20.0)));
	}
	
//	@Test
	public void novoPedido() throws Exception{
		System.out.println(this.mockMvc.perform(get(URL_API+"/pedidos/novo")).andDo(print()));
		this.mockMvc.perform(get(URL_API+"/pedidos/novo"))
					.andExpect(status().isOk())
					.andExpect(jsonPath("valorTotal", is(57.0)))
					.andExpect(jsonPath("pedido", is(4l)))
					.andExpect(jsonPath("mensagem", equalTo("Pedido realizado com sucesso...")));
	}
}
