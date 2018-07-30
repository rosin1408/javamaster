package br.com.biblioteca.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.biblioteca.model.Pessoa;
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
	
	@RequestMapping(value="/pessoa", method=RequestMethod.POST)
	public String salvar(@Valid Pessoa pessoa, BindingResult erros, Model model) {
		if (erros.hasErrors()) {
			model.addAttribute("erros", erros.getAllErrors());
			return "/pessoa/form";
		}
		service.salvar(pessoa);
		
		model.addAttribute("mensagem", "Pessoa salva com sucesso!");
		
		return "/pessoa/form";
	}
	
	@RequestMapping(value="/pessoa/{id}/excluir", method=RequestMethod.GET)
	public String excluir(@PathVariable Long id, Model model) {
		service.excluir(id);
		model.addAttribute("mensagem", "Pessoa excluida com sucesso!");
		
		return "/pessoa/index";
	}

}
