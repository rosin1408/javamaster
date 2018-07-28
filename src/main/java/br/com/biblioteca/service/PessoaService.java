package br.com.biblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.biblioteca.model.Pessoa;
import br.com.biblioteca.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository repository;
	
	public List<Pessoa> listarTodas() {
		return repository.findAll();
	}

	public List<Pessoa> procurarPorNome(String nome) {
		return repository.findByNomeContainingIgnoreCase(nome);
	}

	public Pessoa buscarPorId(long id) {
		return repository.findOne(id);
	}
}
