package com.boaglio.casadocodigo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.boaglio.casadocodigo.model.Cliente;
import com.boaglio.casadocodigo.repositories.ClienteRepository;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepository;
	private final String CLIENTE_URI = "clientes/";
	
	@GetMapping(value="/")
	public ModelAndView list() {
		List<Cliente> clientes = this.clienteRepository.findAll();
		return new ModelAndView("clientes/list","clientes",clientes);
	}
	
	@GetMapping(value="/novo")
	public ModelAndView createForm(@ModelAttribute Cliente cliente) {
		Map<String,List<Cliente>> map = new HashMap<>();
		map.put("clientes", this.clienteRepository.findAll());
		return new ModelAndView("clientes/form",map);
	}
	
	@PostMapping(params= {"form"})
	public ModelAndView create(@Valid Cliente cliente, BindingResult result, RedirectAttributes redirect) {
		if(result.hasErrors()) {
			return new ModelAndView(CLIENTE_URI+"form","formErrors",result.getAllErrors());
		}
		cliente = this.clienteRepository.save(cliente);
		redirect.addFlashAttribute("globalMessage", "Cliente gerado com sucesso...");
		return new ModelAndView("redirect:/"+CLIENTE_URI+"{cliente.id}", "cliente.id", cliente.getId()) ;
	}
	
	@GetMapping(value="{id}")
	public ModelAndView view(@PathVariable("id") Long id) {
		Cliente cliente = this.clienteRepository.findOne(id);
		return new ModelAndView("clientes/view","cliente", cliente);
	}		
	
	@GetMapping(value="/remover/{id}")
	public ModelAndView remover(@PathVariable("id")Long id, RedirectAttributes redirect) {
		this.clienteRepository.delete(id);
		List<Cliente> clientes = this.clienteRepository.findAll();
		ModelAndView mv = new ModelAndView("clientes/list", "clientes", clientes);
		mv.addObject("globalMessage", "Cliente removido com sucesso...");
		return mv;
	}
	
	@GetMapping(value="/alterar/{id}")
	public ModelAndView alterar(@PathVariable("id") Long id) {
		Cliente cliente = this.clienteRepository.findOne(id);
		return new ModelAndView(CLIENTE_URI+"form","cliente", cliente);
	}		
}
