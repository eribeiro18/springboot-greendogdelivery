package com.boaglio.casadocodigo.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.boaglio.casadocodigo.model.Cliente;
import com.boaglio.casadocodigo.model.Item;
import com.boaglio.casadocodigo.model.Pedido;
import com.boaglio.casadocodigo.repositories.ClienteRepository;
import com.boaglio.casadocodigo.repositories.ItemRepository;
import com.boaglio.casadocodigo.repositories.PedidoRepository;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	private Item itemMarcado;	
	private Cliente clienteMarcado;
	private List<Item> itens = new ArrayList<>();
	private Map<String,Object> mapPedidos = new HashMap<>();
	
	private final String PEDIDO_URI = "pedidos/";
	
	public Item getItemMarcado() {
		if(this.itemMarcado == null) {
			this.itemMarcado = new Item();
		}
		return itemMarcado;
	}

	public void setItemMarcado(Item itemMarcado) {
		this.itemMarcado = itemMarcado;
	}
	
	public Cliente getClienteMarcado() {
		if(this.clienteMarcado == null) {
			this.clienteMarcado = new Cliente();
		}
		return clienteMarcado;
	}

	public void setClienteMarcado(Cliente clienteMarcado) {
		this.clienteMarcado = clienteMarcado;
	}

	private void initialize() {
		List<Cliente> clientes = this.clienteRepository.findAll();
		List<Item> itens	   = this.itemRepository.findAll();				
		this.mapPedidos.put("clientes", clientes);
		this.mapPedidos.put("clienteMarcado", this.getClienteMarcado());
		this.mapPedidos.put("itens", itens);
		this.mapPedidos.put("itemMarcado", this.getItemMarcado());
	}

	@GetMapping("/")
	public ModelAndView list() {
		List<Pedido> pedidos = this.pedidoRepository.recuperarPedidos();
		return new ModelAndView(PEDIDO_URI+"list","pedidos",pedidos);
	}	
	
//	@GetMapping(value="/pclientes",produces="application/json;charset=UTF-8")
//	public List<Cliente> clientes(){
//		return this.clienteRepository.findAll();
//	}
	
	@GetMapping("/novo")
	public ModelAndView newTask(@ModelAttribute Pedido pedido) {	
		this.clienteMarcado = null;
		this.mapPedidos.put("pedido",pedido);
		this.initialize();
		return new ModelAndView(PEDIDO_URI+"form",mapPedidos);
	}

	@GetMapping(value="/checkCliente/{cliente}")
	public Cliente checkCliente(@PathVariable("cliente") Long cliente) {
		this.clienteMarcado = this.clienteRepository.findOne(cliente);
		return this.clienteMarcado;
	}
	
	@GetMapping(value="/checkItem/{item}")	
	public String checkItem(@PathVariable("item")Long item){
		String msg = "";		
		Item itemMarcado = this.itemRepository.findOne(item);
		Optional<Item> value = itens.stream().filter(i -> i.getId().equals(itemMarcado.getId())).distinct().findFirst();		
		if(value.isPresent()) {			
			this.itens.remove(value.get());
			msg = "Item removido...";
		} else {
			this.itens.add(itemMarcado);
			msg = "Item selecionado...";
		}		
		return msg;		
	}
	
	@PostMapping(params= {"form"})
	public ModelAndView create(@Valid Pedido pedido, BindingResult result, RedirectAttributes redirect) {
		if(result.hasErrors()) {
			return new ModelAndView(PEDIDO_URI+"form","formErrors",result.getAllErrors());
		}		
		this.initialize();
		if(this.itens.isEmpty()) {				
			ModelAndView mv = new ModelAndView(PEDIDO_URI+"form",this.mapPedidos);
			mv.addObject("globalMessage", "Selecione 1 item para o pedido...");
			return mv;
		}else if(this.clienteMarcado == null || this.clienteMarcado.getId() == null) {
			ModelAndView mv = new ModelAndView(PEDIDO_URI+"form",this.mapPedidos);
			mv.addObject("globalMessage", "Selecione o cliente para o pedido...");
			return mv;
		}else {		
			Pedido p = new Pedido();
			p = pedido;
			p.setCliente(this.clienteMarcado);
			double valorTotal = 0d;
			for(Item item : this.itens) {
				valorTotal += item.getPreco().doubleValue();
			}											
			p.setItens(this.itens);
			p.setData(new Date(System.currentTimeMillis()));
			p.setValorTotal(valorTotal);
			pedido = this.pedidoRepository.saveAndFlush(p);
						
			this.itens.clear();
			this.clienteMarcado = null;
			redirect.addFlashAttribute("globalMessage", "Pedido criado com sucesso...");									
			return new ModelAndView("redirect:/"+PEDIDO_URI);
		}
	}
	
	@PostMapping(value="/update",params= {"form"})
	public ModelAndView update(@Valid Pedido pedido, RedirectAttributes redirect) {
		pedido = this.pedidoRepository.saveAndFlush(pedido);
		
		redirect.addFlashAttribute("globalMessage", "Pedido alterado com sucesso...");
		return new ModelAndView("redirect:/"+PEDIDO_URI);
	}
	
	@GetMapping("/details/{id}")
	public ModelAndView details(@PathVariable("id") Long id) {
	
		Pedido pedido = this.pedidoRepository.findOne(id);
		List<Item> itens = pedido.getItens();
		Cliente cliente  = pedido.getCliente();
		
		double valorTotal = 0d;
		for(Item item : itens) {
			valorTotal += item.getPreco();
		}
		
		pedido.setItens(itens);
		pedido.setCliente(cliente);
		pedido.setValorTotal(valorTotal);
		if(pedido.getData() == null) pedido.setData(new Date(System.currentTimeMillis()));
		
		Map<String,Object> data = new HashMap<>();
		data.put("pedido", pedido);
		
		return new ModelAndView(PEDIDO_URI+"details", data);
	}	
}
