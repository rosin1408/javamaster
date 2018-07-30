package br.com.biblioteca.builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.biblioteca.model.Pessoa;
import br.com.biblioteca.model.Projeto;
import br.com.biblioteca.model.Risco;
import br.com.biblioteca.model.Status;

public class ProjetoBuilder {
	
	private Projeto projeto;
	
	public ProjetoBuilder() {
		Pessoa gerente = new Pessoa();
		gerente.setId(1L);
		gerente.setNome("Nome do Gerente");
		
		this.projeto = new Projeto();
		this.projeto.setId(1L);
		this.projeto.setNome("Nome do projeto");
		this.projeto.setDataInicio(new Date());
		this.projeto.setDataPrevisaoFim(new Date());
		this.projeto.setDataFim(new Date());
		this.projeto.setDescricao("descrição do projeto");
		this.projeto.setStatus(Status.EM_ANALISE);
		this.projeto.setOrcamento(150000.00);
		this.projeto.setRisco(Risco.BAIXO);
		this.projeto.setGerente(gerente);
		this.projeto.setMembros(new ArrayList<>());
	}
	
	public Projeto build() {
		return this.projeto;
	}
	
	public ProjetoBuilder comId(Long id) {
		this.projeto.setId(id);
		return this;
	}
	
	public ProjetoBuilder comGerente(Pessoa gerente) {
		this.projeto.setGerente(gerente);
		return this;
	}

	public ProjetoBuilder comStatus(Status status) {
		this.projeto.setStatus(status);
		return this;
	}
	
	public ProjetoBuilder comMembros(List<Pessoa> membros) {
		this.projeto.setMembros(membros);
		return this;
	}
}
