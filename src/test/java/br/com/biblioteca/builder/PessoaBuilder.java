package br.com.biblioteca.builder;

import java.util.Date;

import br.com.biblioteca.model.Pessoa;

public class PessoaBuilder {
	
	private Pessoa pessoa;
	
	public PessoaBuilder() {
		this.pessoa = new Pessoa();
		this.pessoa.setId(1L);
		this.pessoa.setNome("Nome");
		this.pessoa.setCpf("12345678901");
		this.pessoa.setDataNascimento(new Date());
		this.pessoa.setFuncionario(false);
	}
	
	public Pessoa build() {
		return this.pessoa;
	}
	
	public PessoaBuilder comId(Long id) {
		this.pessoa.setId(id);
		return this;
	}

	public PessoaBuilder comNome(String nome) {
		this.pessoa.setNome(nome);
		return this;
	}
	
	public PessoaBuilder comFuncionario(boolean funcionario) {
		this.pessoa.setFuncionario(funcionario);
		return this;
	}
}
