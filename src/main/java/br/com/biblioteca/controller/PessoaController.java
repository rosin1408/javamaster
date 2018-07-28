package br.com.biblioteca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.biblioteca.service.PessoaService;

@Controller
public class PessoaController {
	
	@Autowired
	private PessoaService service;
	
	@RequestMapping("/pessoa")
	public String home(Model model) {
		model.addAttribute("pessoas", service.listarTodas());
		return "/pessoa/index";
	}
	
	@RequestMapping(value="/pessoa/novo")
	public String novo() {
		return "/pessoa/form";
	}
	
	@RequestMapping(value="/pessoa/{id}", method=RequestMethod.GET)
	public String editar(@PathVariable Long id, Model model) {
		model.addAttribute("pessoa", service.buscarPorId(id));
		
		return "/pessoa/form";
	}

}
