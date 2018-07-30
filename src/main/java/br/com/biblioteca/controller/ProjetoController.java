package br.com.biblioteca.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.biblioteca.exception.ValidationException;
import br.com.biblioteca.model.Projeto;
import br.com.biblioteca.service.PessoaService;
import br.com.biblioteca.service.ProjetoService;

@Controller
public class ProjetoController {
	
	@Autowired
	private ProjetoService service;
	
	@Autowired
	private PessoaService pessoaService;
	
	@RequestMapping("/projeto")
	public String home(Model model) {
		model.addAttribute("projetos", service.listarTodos());
		return "/projeto/index";
	}
	
	@RequestMapping("/projeto/novo")
	public String novo(Model model) {
		model.addAttribute("pessoas", pessoaService.listarTodas());
		return "/projeto/form";
	}
	
	@RequestMapping(value="/projeto/{id}", method=RequestMethod.GET)
	public String editar(@PathVariable Long id, Model model) {
		model.addAttribute("projeto", service.buscarPorId(id));
		model.addAttribute("pessoas", pessoaService.listarTodas());
		
		return "/projeto/form";
	}
	
	@RequestMapping(value="/projeto", method=RequestMethod.POST)
	public String salvar(@Valid Projeto projeto, BindingResult erros, Model model) {
		if (erros.hasErrors()) {
			model.addAttribute("erros", erros.getAllErrors());
			return "/projeto/form";
		}
		service.salvar(projeto);
		
		model.addAttribute("mensagem", "Registro salvo com sucesso!");
		
		return "/projeto/form";
	}
	
	@RequestMapping(value="/projeto/{id}/excluir", method=RequestMethod.GET)
	public String excluir(@PathVariable Long id, Model model) {
		try {
			service.excluir(id);
			model.addAttribute("mensagem", "Projeto excluido com sucesso!");
		} catch (ValidationException e) {
			model.addAttribute("erro", e.getMessage());
			return "/projeto/form";
		}
		return "/projeto/index";
	}
	
	@ResponseBody
	@RequestMapping(value="/projeto/{idProjeto}/adicionarmembro/{idPessoa}", method=RequestMethod.PUT, produces="application/json; charset=UTF-8")
	public String adicionarMembroProjeto(@PathVariable Long idProjeto, @PathVariable Long idPessoa) {
		try {
			this.service.adicionarMembro(idProjeto, idPessoa);
			return String.format("{\"message\": \"%s\"}", "Membro adicionado com sucesso");
		} catch (ValidationException e) {
			return String.format("{\"erro\": \"%s\"}", e.getMessage());
		}
			
	}
}
