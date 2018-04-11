package com.boaglio.casadocodigo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.boaglio.casadocodigo.model.Item;
import com.boaglio.casadocodigo.repositories.ItemRepository;

@RestController
@RequestMapping("/itens/")
public class ItemController {

	@Autowired
	private ItemRepository itemRepository;
	private final String ITENS_URI = "itens/";
	
	@GetMapping("/")
	public ModelAndView list() {
		List<Item> itens = this.itemRepository.findAll();
		return new ModelAndView(ITENS_URI+"list", "itens", itens);
	}
	
	@GetMapping("{id}")
	public ModelAndView view(@PathVariable("id") Long id) {
		Item item = this.itemRepository.findOne(id);
		return new ModelAndView(ITENS_URI+"view","item",item);
	}
	
	@GetMapping("/novo")
	public ModelAndView newItem(@ModelAttribute Item item) {
		List<Item> itens = this.itemRepository.findAll();
		Map<String, Object> values = new HashMap<>();
		values.put("item", item);
		values.put("itens", itens);
		return new ModelAndView(ITENS_URI+"form",values);
	}
	
	@PostMapping(params={"form"})
	public ModelAndView create(@Valid Item item, BindingResult result, RedirectAttributes redirect) {
		if(result.hasErrors()) {
			return new ModelAndView(ITENS_URI+"form", "formErrors", result.getAllErrors());
		}
		item = this.itemRepository.saveAndFlush(item);
		redirect.addFlashAttribute("globalMessage","Item cadastrado com sucesso...");
		return new ModelAndView("redirect:/"+ITENS_URI+"{item.id}", "item.id", item.getId());
	}
	
	@GetMapping("/remover/{id}")
	public ModelAndView remover(@PathVariable("id")Long id) {
		Item item = itemRepository.findOne(id);
		this.itemRepository.delete(item);
		
		List<Item> itens = this.itemRepository.findAll();
		return new ModelAndView(ITENS_URI+"list", "itens", itens);
	}
	
	@GetMapping("/alterar/{id}")
	public ModelAndView alterar(@PathVariable("id")Long id) {
		Item item = this.itemRepository.findOne(id);
		return new ModelAndView(ITENS_URI+"form","item",item);
	}
}
